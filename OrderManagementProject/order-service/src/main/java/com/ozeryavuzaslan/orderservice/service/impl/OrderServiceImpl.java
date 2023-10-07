package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.client.PaymentServiceClient;
import com.ozeryavuzaslan.orderservice.client.RevenueServiceClient;
import com.ozeryavuzaslan.orderservice.client.StockServiceClient;
import com.ozeryavuzaslan.orderservice.model.Order;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.OrderRepository;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import com.ozeryavuzaslan.orderservice.service.PriceCalculationService;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final StockServiceClient stockServiceClient;
    private final OrderPropertySetter orderPropertySetter;
    private final StockPropertySetter stockPropertySetter;
    private final RevenueServiceClient revenueServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final PaymentPropertySetter paymentPropertySetter;
    private final PriceCalculationService priceCalculationService;
    private final PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService;

    @Override
    @Transactional
    public OrderDTO takeOrder(OrderDTO orderDTO) {
        Order order = orderPropertySetter.setSomeProperties(orderDTO);
        modelMapper.map(orderRepository.save(order), orderDTO);
        List<ReservedStockDTO> reservedStockDTOList = stockPropertySetter.setSomeProperties(orderDTO);

        try {
            reservedStockDTOList = redirectReserveStocks(reservedStockDTOList);
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda saga pattern cancel işlemini uygula
            return null;
        }

        TaxRateDTO taxRateDTO;

        try {
            taxRateDTO = redirectGetSpecificTaxRate();
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda circuitBraker çalışacak şekilde kontrol yap
            return null;
        }

        priceCalculationService.calculateOrderPrice(reservedStockDTOList, taxRateDTO, orderDTO);
        paymentPropertySetter.setSomeProperties(orderDTO, paymentRequestDTOForPaymentService);

        try {
            //TODO:Payment servisi içerisinde stripe kullanıcısı yoksa exception dönecek şekilde handle et
            redirectMakePayment(orderDTO, paymentRequestDTOForPaymentService);
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda saga pattern cancel işlemini uygula
            return null;
        }

        try {
            reservedStockDTOList = redirectDecreaseStocks(reservedStockDTOList);
        } catch (FeignException feignException) {
            int responseStatusCode = feignException.status();
            HttpStatus httpStatus = HttpStatus.valueOf(responseStatusCode);

            //TODO: Exception olduğunda saga pattern cancel işlemini uygula
            return null;
        }

        orderPropertySetter.setReserveType(orderDTO);
        modelMapper.map(orderDTO, order);
        modelMapper.map(orderRepository.save(order), orderDTO);
        return orderDTO;
    }

    //TODO:CircuitBreaker uygula
    private List<ReservedStockDTO> redirectReserveStocks(List<ReservedStockDTO> reservedStockDTOList) {
        return stockServiceClient.reserveStock(reservedStockDTOList);
    }

    //TODO:CircuitBreaker uygula
    private TaxRateDTO redirectGetSpecificTaxRate() {
        LocalDate currentDate = LocalDate.now();
        int taxYear = currentDate.getYear();
        int taxMonth = currentDate.getMonthValue();
        return revenueServiceClient.getSpecificTaxRate(taxYear, taxMonth, TaxRateType.KDV);
    }

    //TODO:CircuitBreaker uygula
    private void redirectMakePayment(OrderDTO orderDTO,
                                     PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService) {
        switch (orderDTO.getPaymentProviderType()) {
            case STRIPE -> {
                StripePaymentResponseDTO stripePaymentResponseDTO = paymentServiceClient.payViaStripe(paymentRequestDTOForPaymentService.getStripePaymentRequestDTO());
                orderPropertySetter.setSomeProperties(orderDTO, stripePaymentResponseDTO);
            }
            case PAYPAL -> {}
            case CREDIT_CARD -> {}
        }
    }

    //TODO:CircuitBreaker uygula
    private List<ReservedStockDTO> redirectDecreaseStocks(List<ReservedStockDTO> reservedStockDTOList){
        return stockServiceClient.decreaseStocks(reservedStockDTOList);
    }
}