package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.util.HandledHTTPExceptions;
import com.ozeryavuzaslan.orderservice.client.PaymentServiceClient;
import com.ozeryavuzaslan.orderservice.client.RevenueServiceClient;
import com.ozeryavuzaslan.orderservice.client.StockServiceClient;
import com.ozeryavuzaslan.orderservice.dto.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.orderservice.dto.RefundRequestDTOForPaymentService;
import com.ozeryavuzaslan.orderservice.exception.*;
import com.ozeryavuzaslan.orderservice.service.FailedOrderService;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import com.ozeryavuzaslan.orderservice.service.SagaRollbackChainService;
import feign.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RedirectAndFallbackHandlerImpl implements RedirectAndFallbackHandler {
    private final StockServiceClient stockServiceClient;
    private final FailedOrderService failedOrderService;
    private final PaymentServiceClient paymentServiceClient;
    private final RevenueServiceClient revenueServiceClient;
    private final SagaRollbackChainService sagaRollbackChainService;

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
        try {
            return stockServiceClient.reserveStock(reservedStockDTOList);
        } catch (Exception exception) {
            throw new CustomOrderServiceException(exception.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getPaymentFallbackMethod")
    public Response redirectMakePayment(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService, List<ReservedStockDTO> reservedStockDTOList) {
        try {
            switch (orderDTO.getPaymentProviderType()) {
                case STRIPE -> {
                    return paymentServiceClient.payViaStripe(paymentRequestDTOForPaymentService.getStripePaymentRequestDTO());
                }
                case PAYPAL, CREDIT_CARD -> {
                    return null;
                }
            }

            return null;
        } catch (Exception exception) {
            throw new CustomOrderServiceException(exception.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getStockFallbackMethod")
    public Response redirectDecreaseStocks(List<ReservedStockDTO> reservedStockDTOList, OrderDTO orderDTO) {
        try {
            return stockServiceClient.decreaseStocks(reservedStockDTOList);
        } catch (Exception exception) {
            throw new CustomOrderServiceException(exception.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getRevenueFallbackMethod")
    public Response redirectGetSpecificTaxRate(List<ReservedStockDTO> reservedStockDTOList) {
        try {
            LocalDate currentDate = LocalDate.now();
            int taxYear = currentDate.getYear();
            int taxMonth = currentDate.getMonthValue();
            return revenueServiceClient.getSpecificTaxRate(taxYear, taxMonth, TaxRateType.KDV);
        } catch (Exception exception) {
            throw new CustomOrderServiceException(exception.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getReserveStockFallbackMethod")
    public Response redirectRollbackReservedStocks(List<ReservedStockDTO> reservedStockDTOList) {
        try {
            return stockServiceClient.rollbackReservedStocks(reservedStockDTOList);
        } catch (Exception exception) {
            throw new CustomOrderServiceException(exception.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getRollbackPaymentFallbackMethod")
    public Response redirectRollbackPayment(OrderDTO orderDTO, RefundRequestDTOForPaymentService refundRequestDTOForPaymentService) {
        try {
            switch (orderDTO.getPaymentProviderType()) {
                case STRIPE -> {
                    return paymentServiceClient.refundViaStripe(refundRequestDTOForPaymentService.getStripeRefundRequestDTO());
                }
                case PAYPAL, CREDIT_CARD -> {
                    return null;
                }
            }

            return null;
        } catch (Exception exception) {
            throw new CustomOrderServiceException(exception.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getStockFallbackMethod")
    public Response redirectRollbackStock(List<StockDTO> stockDTOList) {
        try {
            return stockServiceClient.rollbackDecreasedStocks(stockDTOList);
        } catch (Exception exception) {
            throw new CustomOrderServiceException(exception.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getStockFallbackMethodFromCancellationMethod")
    public Response redirectRollbackStocksAndReservedStocksByOrderID(OrderDTO orderDTO) {
        try {
            return stockServiceClient.rollbackStocksAndReservedStocksByOrderID(orderDTO.getId());
        } catch (Exception exception) {
            throw new CustomOrderServiceException(exception.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getReserveStockFallbackMethod")
    public Response redirectGetReservedStocksByOrderID(OrderDTO orderDTO) {
        try {
            return stockServiceClient.getReservedStocksByOrderID(orderDTO.getId());
        } catch (Exception exception) {
            throw new CustomOrderServiceException(exception.getMessage());
        }
    }

    private Response getRevenueFallbackMethod(List<ReservedStockDTO> reservedStockDTOList, Exception exception) {
        int statusCode = sagaRollbackChainService.beginRollbackChainPhase1(reservedStockDTOList);

        if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode))
            failedOrderService.insertFailedOrderAndRollbackPhase(reservedStockDTOList);

        throw new RevenueServiceNotRunningException(revenueServiceNotRunning);
    }

    private Response getStockFallbackMethod(List<ReservedStockDTO> reservedStockDTOList, OrderDTO orderDTO, Exception exception) {
        failedOrderService.insertFailedOrderAndRollbackPhase(orderDTO, reservedStockDTOList);
        throw new StockServiceNotRunningException(stockServiceNotRunning);
    }

    private Response getPaymentFallbackMethod(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService, List<ReservedStockDTO> reservedStockDTOList, Exception exception) {
        int statusCode = sagaRollbackChainService.beginRollbackChainPhase1(reservedStockDTOList);

        if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode))
            failedOrderService.insertFailedOrderAndRollbackPhase(reservedStockDTOList);

        throw new PaymentServiceNotRunningException(paymentServiceNotRunning);
    }

    private Response getRollbackPaymentFallbackMethod(OrderDTO orderDTO, RefundRequestDTOForPaymentService refundRequestDTOForPaymentService, Exception exception) {
        failedOrderService.insertFailedOrderAndRollbackPhase(orderDTO);
        throw new PaymentServiceNotRunningException(paymentServiceNotRunning);
    }

    private Response getReserveStockFallbackMethod(Exception exception) {
        throw new ReserveStockServiceNotRunningException(reserveStockServiceNotRunning);
    }

    private Response getStockFallbackMethodFromCancellationMethod(OrderDTO orderDTO, Exception exception) {
        failedOrderService.insertFailedOrderAndRollbackPhase(orderDTO);
        throw new StockServiceNotRunningException(stockServiceNotRunning);
    }
}