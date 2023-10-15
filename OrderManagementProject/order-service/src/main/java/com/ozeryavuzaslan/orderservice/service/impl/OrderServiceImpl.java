package com.ozeryavuzaslan.orderservice.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.util.HandledHTTPExceptions;
import com.ozeryavuzaslan.orderservice.kafka.OrderProducer;
import com.ozeryavuzaslan.orderservice.model.Order;
import com.ozeryavuzaslan.orderservice.model.OrderStock;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.OrderRepository;
import com.ozeryavuzaslan.orderservice.service.BeginSagaRollbackChain;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import com.ozeryavuzaslan.orderservice.service.PriceCalculationService;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;
    private final OrderPropertySetter orderPropertySetter;
    private final StockPropertySetter stockPropertySetter;
    private final PaymentPropertySetter paymentPropertySetter;
    private final BeginSagaRollbackChain beginSagaRollbackChain;
    private final PriceCalculationService priceCalculationService;
    private final RedirectAndFallbackHandler redirectAndFallbackHandler;
    private final PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService;

    @Override
 //   @Transactional
    //TODO: Yeni eklenen global DTO'ların validasyonlarını ekle
    //TODO: CircuitBreaker'a girerse de Saga Rollback implementasyonlarını yaz
    // Bunun için uygun bir DB de oluştur. Service not running exceptionından önce db'den gerekli bilgileri alıp, stocktan geri düşürme, paymentten geri ödeme vb. yapsın.
    public OrderDTO takeOrder(OrderDTO orderDTO) throws Exception {
        Order order = orderPropertySetter.setSomeProperties(orderDTO);
        modelMapper.map(orderRepository.save(order), orderDTO);
        List<ReservedStockDTO> reservedStockDTOList = stockPropertySetter.setSomeProperties(orderDTO);
        int statusCode;

        try (Response reserveStockResponse = redirectAndFallbackHandler.redirectReserveStocks(reservedStockDTOList)) {
            statusCode = reserveStockResponse.status();

            if (HandledHTTPExceptions.checkKnownException(statusCode))
                throw new RuntimeException(objectMapper.readValue(reserveStockResponse.body().asInputStream(), ErrorDetailsDTO.class).getMessage() + "_" + statusCode);

            JavaType reservedStockDTOType = objectMapper.getTypeFactory().constructCollectionType(List.class, ReservedStockDTO.class);
            reservedStockDTOList = objectMapper.readValue(reserveStockResponse.body().asInputStream(), reservedStockDTOType);
        } catch (IOException e) {
            throw new Exception(e);
        }

        orderPropertySetter.setSomeProperties(reservedStockDTOList, order);
        order.getOrderStockList().sort(Comparator.comparing(OrderStock::getId));
        orderRepository.save(order);
        TaxRateDTO taxRateDTO;

        try (Response taxRateResponse = redirectAndFallbackHandler.redirectGetSpecificTaxRate()) {
            statusCode = taxRateResponse.status();

            if (HandledHTTPExceptions.checkKnownException(statusCode)) {
                beginSagaRollbackChain.beginRollbackFromReservedStocksPhase1(reservedStockDTOList);
                ErrorDetailsDTO errorDetailsDTO = objectMapper.readValue(taxRateResponse.body().asInputStream(), ErrorDetailsDTO.class);
                throw new RuntimeException(errorDetailsDTO.getMessage() + "_" + statusCode);
            }

            taxRateDTO = objectMapper.readValue(taxRateResponse.body().asInputStream(), TaxRateDTO.class);
        } catch (IOException e) {
            throw new Exception(e);
        }

        priceCalculationService.calculateOrderPrice(reservedStockDTOList, taxRateDTO, orderDTO);
        paymentPropertySetter.setSomeProperties(orderDTO, paymentRequestDTOForPaymentService);

        try (Response paymentResponse = redirectAndFallbackHandler.redirectMakePayment(orderDTO, paymentRequestDTOForPaymentService)) {
            switch (orderDTO.getPaymentProviderType()) {
                case STRIPE -> {
                    statusCode = paymentResponse.status();

                    if (HandledHTTPExceptions.checkKnownException(statusCode)) { //TODO: Exception anında SAGA rollback uygula
                        ErrorDetailsDTO errorDetailsDTO = objectMapper.readValue(paymentResponse.body().asInputStream(), ErrorDetailsDTO.class);
                        throw new RuntimeException(errorDetailsDTO.getMessage() + "_" + statusCode);
                    }

                    StripePaymentResponseDTO stripePaymentResponseDTO = objectMapper.readValue(paymentResponse.body().asInputStream(), StripePaymentResponseDTO.class);
                    orderPropertySetter.setSomeProperties(orderDTO, stripePaymentResponseDTO);
                }
                case PAYPAL, CREDIT_CARD -> {
                }
            }
        } catch (IOException e) {
            throw new Exception(e);
        }

        try (Response reserveStockResponse = redirectAndFallbackHandler.redirectDecreaseStocks(reservedStockDTOList)) {
            statusCode = reserveStockResponse.status();

            if (HandledHTTPExceptions.checkKnownException(statusCode)) { //TODO: Exception anında SAGA rollback uygula
                ErrorDetailsDTO errorDetailsDTO = objectMapper.readValue(reserveStockResponse.body().asInputStream(), ErrorDetailsDTO.class);
                throw new RuntimeException(errorDetailsDTO.getMessage() + "_" + statusCode);
            }

            JavaType reservedStockDTOType = objectMapper.getTypeFactory().constructCollectionType(List.class, ReservedStockDTO.class);
            reservedStockDTOList = objectMapper.readValue(reserveStockResponse.body().asInputStream(), reservedStockDTOType);
        } catch (IOException e) {
            throw new Exception(e);
        }

        orderPropertySetter.setReserveType(orderDTO);
        modelMapper.map(orderDTO, order);
        modelMapper.map(orderRepository.save(order), orderDTO);
        orderProducer.sendMessage(orderDTO);
        return orderDTO;
    }
}