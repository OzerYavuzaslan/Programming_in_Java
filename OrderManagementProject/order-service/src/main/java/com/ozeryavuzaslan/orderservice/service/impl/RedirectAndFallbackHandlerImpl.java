package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.client.PaymentServiceClient;
import com.ozeryavuzaslan.orderservice.client.RevenueServiceClient;
import com.ozeryavuzaslan.orderservice.client.StockServiceClient;
import com.ozeryavuzaslan.orderservice.exception.PaymentServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.exception.ReserveStockServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.exception.RevenueServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.exception.StockServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import feign.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedirectAndFallbackHandlerImpl implements RedirectAndFallbackHandler {
    private final StockServiceClient stockServiceClient;
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
    public Response redirectReserveStocks(List<ReservedStockDTO> reservedStockDTOList) {
        return stockServiceClient.reserveStock(reservedStockDTOList);
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getPaymentFallbackMethod")
    public Response redirectMakePayment(OrderDTO orderDTO,
                                        PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService) {
        switch (orderDTO.getPaymentProviderType()) {
            case STRIPE -> {
                return paymentServiceClient.payViaStripe(paymentRequestDTOForPaymentService.getStripePaymentRequestDTO());
            }
            case PAYPAL, CREDIT_CARD -> {return null;}
        }

        return null;
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getStockFallbackMethod")
    public Response redirectDecreaseStocks(List<ReservedStockDTO> reservedStockDTOList) {
        return stockServiceClient.decreaseStocks(reservedStockDTOList);
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getRevenueFallbackMethod")
    public Response redirectGetSpecificTaxRate() {
        LocalDate currentDate = LocalDate.now();
        int taxYear = currentDate.getYear();
        int taxMonth = currentDate.getMonthValue();
        return revenueServiceClient.getSpecificTaxRate(taxYear, taxMonth, TaxRateType.KDV);
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getReserveStockFallbackMethod")
    public Response redirectRollbackReservedStocks(List<ReservedStockDTO> reservedStockDTOList) {
        return stockServiceClient.rollbackReservedStocks(reservedStockDTOList);
    }

    private Response getRevenueFallbackMethod(Exception exception) {
        throw new RevenueServiceNotRunningException(revenueServiceNotRunning);
    }

    private Response getStockFallbackMethod(Exception exception) {
        throw new StockServiceNotRunningException(stockServiceNotRunning);
    }

    private Response getPaymentFallbackMethod(Exception exception) {
        throw new PaymentServiceNotRunningException(paymentServiceNotRunning);
    }

    private Response getReserveStockFallbackMethod(Exception exception) {
        throw new ReserveStockServiceNotRunningException(reserveStockServiceNotRunning);
    }
}