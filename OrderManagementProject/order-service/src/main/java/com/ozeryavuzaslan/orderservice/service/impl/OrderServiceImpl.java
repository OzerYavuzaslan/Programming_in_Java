package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.kafka.OrderProducer;
import com.ozeryavuzaslan.orderservice.model.Order;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.OrderRepository;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import com.ozeryavuzaslan.orderservice.service.PriceCalculationService;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;
    private final OrderPropertySetter orderPropertySetter;
    private final StockPropertySetter stockPropertySetter;
    private final PaymentPropertySetter paymentPropertySetter;
    private final PriceCalculationService priceCalculationService;
    private final RedirectAndFallbackHandler redirectAndFallbackHandler;
    private final PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService;

    @Override
    @Transactional
    public OrderDTO takeOrder(OrderDTO orderDTO) {
        Order order = orderPropertySetter.setSomeProperties(orderDTO);
        modelMapper.map(orderRepository.save(order), orderDTO);
        List<ReservedStockDTO> reservedStockDTOList = stockPropertySetter.setSomeProperties(orderDTO);

        try {
            reservedStockDTOList = redirectAndFallbackHandler.redirectReserveStocks(reservedStockDTOList);
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda saga pattern cancel işlemini uygula
            return null;
        }

        TaxRateDTO taxRateDTO;

        try {
            taxRateDTO = redirectAndFallbackHandler.redirectGetSpecificTaxRate();
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda saga pattern cancel işlemini uygula
            return null;
        }

        priceCalculationService.calculateOrderPrice(reservedStockDTOList, taxRateDTO, orderDTO);
        paymentPropertySetter.setSomeProperties(orderDTO, paymentRequestDTOForPaymentService);

        try {
            //TODO:Payment servisi içerisinde stripe kullanıcısı yoksa exception dönecek şekilde handle et
            redirectAndFallbackHandler.redirectMakePayment(orderDTO, paymentRequestDTOForPaymentService);
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda saga pattern cancel işlemini uygula
            return null;
        }

        try {
            reservedStockDTOList = redirectAndFallbackHandler.redirectDecreaseStocks(reservedStockDTOList);
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda saga pattern cancel işlemini uygula
            return null;
        }

        orderPropertySetter.setReserveType(orderDTO);
        modelMapper.map(orderDTO, order);
        modelMapper.map(orderRepository.save(order), orderDTO);
        orderProducer.sendMessage(orderDTO);
        return orderDTO;
    }
}