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
import com.ozeryavuzaslan.orderservice.exception.CustomServiceException;
import com.ozeryavuzaslan.orderservice.kafka.OrderProducer;
import com.ozeryavuzaslan.orderservice.model.Order;
import com.ozeryavuzaslan.orderservice.model.OrderStock;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.OrderRepository;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import com.ozeryavuzaslan.orderservice.service.PriceCalculationService;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import com.ozeryavuzaslan.orderservice.service.SagaRollbackChainService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    private final PlatformTransactionManager transactionManager;
    private final PriceCalculationService priceCalculationService;
    private final SagaRollbackChainService sagaRollbackChainService;
    private final RedirectAndFallbackHandler redirectAndFallbackHandler;
    private final PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService;

    /**
     * Servisler arası haberleşme feignClient ile senkrondur. Email servis hariç
     * Her şey yolunda giderse, önce rezerv yapıyor (stock servisin reserv ile ilgili controllerına istek atıyor), sonra revenue servisten taxRate getiriyor
     * TaxRate'i aldıktan sonra ödenecek tutarı hesaplıyor. İndirim varsa ona göre hesaplıyor.
     * Ödeme yapıldıktan sonra Order'ın payment durumunu güncelliyor.
     * Ödemeyi yaptıktan sonra bu sefer fiziki olarak stocktan miktar düşmek için tekrar stock servise gidiyor.
     * Bundan sonra siparişi kayıt edip asenkron bir şekilde email servisin dinlediği kuyruğa mesaj bırakıyor.
     * <p>
     * Bu işlemler esnasında herhangi bir serviste exception alır ya da servislerden en az biri erişilemez olursa,
     * exception aldığı noktadan geriye doğru rollback işlemleri için bir zincirleme hareket başlatıyor.
     * Rollback esnasında da başka bir exception gelirse, bu sefer de DB'ye kayıt ediyor. Hangi phasedeyken rollback işlemleri başarısız olduysa onun kaydını tutuyor.
     *
     * @param orderDTO
     * @return orderDTO
     * @throws Exception
     */
    //TODO: Bütün servislere loglama eklemeyi unutma
    // ErrorDetailsDTO'yu güncelle saga rollback yaparken birden fazla serviste exception olursa client tarafına bütün exceptionları dönecek şekilde olsun.
    // Yani array şeklinde.
    // Yeni eklenen global DTO'ların validasyonlarını ekle
    // CircuitBreaker'a girerse de Saga Rollback implementasyonlarını yaz
    @Override
    public OrderDTO takeOrder(OrderDTO orderDTO) throws Exception {
        Order order;
        List<ReservedStockDTO> reservedStockDTOList;
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            order = orderPropertySetter.setSomeProperties(orderDTO);
            modelMapper.map(orderRepository.save(order), orderDTO);
            reservedStockDTOList = stockPropertySetter.setSomeProperties(orderDTO);
            JavaType reservedStockDTOType = objectMapper.getTypeFactory().constructCollectionType(List.class, ReservedStockDTO.class);
            int statusCode;

            try (Response reserveStockResponse = redirectAndFallbackHandler.redirectReserveStocks(reservedStockDTOList)) {
                statusCode = reserveStockResponse.status();

                if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode))
                    throw new CustomServiceException(objectMapper.readValue(reserveStockResponse.body().asInputStream(), ErrorDetailsDTO.class).getMessage() + "_" + statusCode);

                reservedStockDTOList = objectMapper.readValue(reserveStockResponse.body().asInputStream(), reservedStockDTOType);
            } catch (IOException e) {
                throw new Exception(e);
            }

            orderPropertySetter.setSomeProperties(reservedStockDTOList, order);
            order.getOrderStockList().sort(Comparator.comparing(OrderStock::getId));
            orderRepository.save(order);
            TaxRateDTO taxRateDTO;

            try (Response taxRateResponse = redirectAndFallbackHandler.redirectGetSpecificTaxRate()) {
                sagaRollbackChainService.checkResponseAndBeginRollbackPhase1IfFailed(taxRateResponse.status(), orderDTO, reservedStockDTOList, taxRateResponse);
                taxRateDTO = objectMapper.readValue(taxRateResponse.body().asInputStream(), TaxRateDTO.class);
            } catch (IOException e) {
                throw new Exception(e);
            }

            priceCalculationService.calculateOrderPrice(reservedStockDTOList, taxRateDTO, orderDTO);
            paymentPropertySetter.setSomeProperties(orderDTO, paymentRequestDTOForPaymentService);

            try (Response paymentResponse = redirectAndFallbackHandler.redirectMakePayment(orderDTO, paymentRequestDTOForPaymentService)) {
                switch (orderDTO.getPaymentProviderType()) {
                    case STRIPE -> {
                        sagaRollbackChainService.checkResponseAndBeginRollbackPhase1IfFailed(paymentResponse.status(), orderDTO, reservedStockDTOList, paymentResponse);
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
                sagaRollbackChainService.checkResponseAndBeginRollbackPhase2IfFailed(reserveStockResponse.status(), orderDTO, reservedStockDTOList, reserveStockResponse);
                reservedStockDTOList = objectMapper.readValue(reserveStockResponse.body().asInputStream(), reservedStockDTOType);
            } catch (IOException e) {
                throw new Exception(e);
            }
        } catch (Exception exception) {
            if (!status.isCompleted())
                transactionManager.rollback(status);

            throw exception;
        }

        try {
            orderPropertySetter.setReserveType(orderDTO);
            modelMapper.map(orderDTO, order);
            modelMapper.map(orderRepository.save(order), orderDTO);
            transactionManager.commit(status);
            orderProducer.sendMessage(orderDTO);
        } catch (Exception exception) {
            sagaRollbackChainService.preRollbackChainPhase3(orderDTO, reservedStockDTOList);

            if (!status.isCompleted())
                transactionManager.rollback(status);

            throw exception;
        }

        return orderDTO;
    }
}