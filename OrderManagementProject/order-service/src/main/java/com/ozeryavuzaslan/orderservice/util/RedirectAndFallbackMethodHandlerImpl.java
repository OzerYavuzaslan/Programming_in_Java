package com.ozeryavuzaslan.orderservice.util;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.util.RedirectAndFallbackHandler;
import com.ozeryavuzaslan.orderservice.client.PaymentServiceClient;
import com.ozeryavuzaslan.orderservice.client.RevenueServiceClient;
import com.ozeryavuzaslan.orderservice.client.StockServiceClient;
import com.ozeryavuzaslan.orderservice.exception.PaymentServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.exception.ReserveStockServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.exception.RevenueServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.exception.StockServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RedirectAndFallbackMethodHandlerImpl implements RedirectAndFallbackHandler {
    private final StockServiceClient stockServiceClient;
    private final OrderPropertySetter orderPropertySetter;
    private final PaymentServiceClient paymentServiceClient;
    private final RevenueServiceClient revenueServiceClient;

    @Value("${reserve.stock.service.not.running.exception}" + "${base.service.not.running.exception.msg}")
    private String reserveStockServiceNotRunning;

    @Value("${stock.service.not.running.exception}" + "${base.service.not.running.exception.msg}")
    private String stockServiceNotRunning;

    @Value("${payment.service.not.running.exception}" + "${base.service.not.running.exception.msg}")
    private String paymentServiceNotRunning;

    @Value("${revenue.service.not.running.exception}" + "${base.service.not.running.exception.msg}")
    private String revenueServiceNotRunning;

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getReserveStockFallbackMethod")
    public List<ReservedStockDTO> redirectReserveStocks(List<ReservedStockDTO> reservedStockDTOList) {
        return stockServiceClient.reserveStock(reservedStockDTOList);
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getPaymentFallbackMethod")
    public void redirectMakePayment(OrderDTO orderDTO,
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

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getStockFallbackMethod")
    public List<ReservedStockDTO> redirectDecreaseStocks(List<ReservedStockDTO> reservedStockDTOList){
        return stockServiceClient.decreaseStocks(reservedStockDTOList);
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getRevenueFallbackMethod")
    public TaxRateDTO redirectGetSpecificTaxRate() {
        LocalDate currentDate = LocalDate.now();
        int taxYear = currentDate.getYear();
        int taxMonth = currentDate.getMonthValue();
        return revenueServiceClient.getSpecificTaxRate(taxYear, taxMonth, TaxRateType.KDV);
    }

    private TaxRateDTO getRevenueFallbackMethod(Exception exception) {
        throw new RevenueServiceNotRunningException(revenueServiceNotRunning);
    }

    private List<ReservedStockDTO> getStockFallbackMethod(Exception exception) {
        throw new StockServiceNotRunningException(stockServiceNotRunning);
    }

    private void getPaymentFallbackMethod(Exception exception) {
        throw new PaymentServiceNotRunningException(paymentServiceNotRunning);
    }

    private List<ReservedStockDTO> getReserveStockFallbackMethod(Exception exception) {
        throw new ReserveStockServiceNotRunningException(reserveStockServiceNotRunning);
    }
}