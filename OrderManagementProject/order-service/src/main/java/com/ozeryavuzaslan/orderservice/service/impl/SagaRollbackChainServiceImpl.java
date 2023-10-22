package com.ozeryavuzaslan.orderservice.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.util.HandledHTTPExceptions;
import com.ozeryavuzaslan.basedomains.util.TypeFactoryHelper;
import com.ozeryavuzaslan.orderservice.dto.RefundRequestDTOForPaymentService;
import com.ozeryavuzaslan.orderservice.exception.CustomOrderServiceException;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackReason;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.orderservice.service.FailedOrderService;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import com.ozeryavuzaslan.orderservice.service.SagaRollbackChainService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SagaRollbackChainServiceImpl implements SagaRollbackChainService {
    private final ObjectMapper objectMapper;
    private final FailedOrderService failedOrderService;
    private final StockPropertySetter stockPropertySetter;
    private final PaymentPropertySetter paymentPropertySetter;
    private final RedirectAndFallbackHandler redirectAndFallbackHandler;
    private final RefundRequestDTOForPaymentService refundRequestDTOForPaymentService;

    @Value("${order.not.canceled.exception.v2}")
    private String orderNotCanceledMsg;

    @Override
    public void checkResponseAndBeginRollbackPhase1IfFailed(int statusCode, OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList, Response response) throws Exception {
        try {
            if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode)) {
                statusCode = beginRollbackChainPhase1(reservedStockDTOList);

                if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode))
                    failedOrderService.insertFailedOrderAndRollbackPhase(reservedStockDTOList);

                ErrorDetailsDTO errorDetailsDTO = objectMapper.readValue(response.body().asInputStream(), ErrorDetailsDTO.class);
                throw new CustomOrderServiceException(errorDetailsDTO.getMessage() + "_" + statusCode);
            }
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void checkResponseAndBeginRollbackPhase2IfFailed(int statusCode, OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList, Response response) throws Exception {
        try {
            if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode)) {
                statusCode = beginRollbackChainPhase2(orderDTO, reservedStockDTOList);

                if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode))
                    failedOrderService.insertFailedOrderAndRollbackPhase(orderDTO, reservedStockDTOList);

                ErrorDetailsDTO errorDetailsDTO = objectMapper.readValue(response.body().asInputStream(), ErrorDetailsDTO.class);
                throw new CustomOrderServiceException(errorDetailsDTO.getMessage() + "_" + statusCode);
            }
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void rollbackChainPhase3(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList) {
        int statusCode = beginRollbackChainPhase3(orderDTO, reservedStockDTOList);

        if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode))
            failedOrderService.insertFailedOrderAndRollbackPhase(reservedStockDTOList, orderDTO);
    }

    @Override
    public int beginRollbackChainPhase1(List<ReservedStockDTO> reservedStockDTOList) {
        try (Response responseRollbackReservedStocks = redirectAndFallbackHandler.redirectRollbackReservedStocks(reservedStockDTOList)) {
            return responseRollbackReservedStocks.status();
        }
    }

    @Override
    public int beginRollbackChainPhase2(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList) {
        paymentPropertySetter.setSomeProperties(orderDTO, refundRequestDTOForPaymentService);

        try (Response responseRollbackPayment = redirectAndFallbackHandler.redirectRollbackPayment(orderDTO, refundRequestDTOForPaymentService)) {
            int statusCode = responseRollbackPayment.status();

            if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode))
                return statusCode;

            return beginRollbackChainPhase1(reservedStockDTOList);
        }
    }

    @Override
    public int beginRollbackChainPhase3(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList) {
        List<StockDTO> rollbackStockDTOList = stockPropertySetter.setSomeProperties(reservedStockDTOList);

        try (Response stockResponse = redirectAndFallbackHandler.redirectRollbackStock(rollbackStockDTOList)) {
            int statusCode = stockResponse.status();

            if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode))
                return statusCode;

            return beginRollbackChainPhase2(orderDTO, reservedStockDTOList);
        }
    }

    @Override
    public void beginOrderCancellation(OrderDTO orderDTO, Response response) throws Exception {
        int statusCode = response.status();

        try {
            if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode)) {
                ErrorDetailsDTO errorDetailsDTO = objectMapper.readValue(response.body().asInputStream(), ErrorDetailsDTO.class);
                failedOrderService.insertFailedOrderAndRollbackPhase(RollbackReason.CANCELED_BY_CUSTOMER, orderDTO, null);
                throw new CustomOrderServiceException(errorDetailsDTO.getMessage() + "_" + statusCode);
            }

            JavaType reservedStockDTOType = TypeFactoryHelper.constructCollectionType(ReservedStockDTO.class, objectMapper);
            List<ReservedStockDTO> reservedStockDTOList = objectMapper.readValue(response.body().asInputStream(), reservedStockDTOType);
            paymentPropertySetter.setSomeProperties(orderDTO, refundRequestDTOForPaymentService);

            try (Response responseRollbackPayment = redirectAndFallbackHandler.redirectRollbackPayment(orderDTO, refundRequestDTOForPaymentService)) {
                statusCode = responseRollbackPayment.status();

                if (HandledHTTPExceptions.checkHandledExceptionStatusCode(statusCode)) {
                    failedOrderService.insertFailedOrderAndRollbackPhase(RollbackReason.CANCELED_BY_CUSTOMER, orderDTO, reservedStockDTOList);
                    ErrorDetailsDTO errorDetailsDTO = objectMapper.readValue(response.body().asInputStream(), ErrorDetailsDTO.class);
                    throw new CustomOrderServiceException(errorDetailsDTO.getMessage() + "_" + statusCode);
                }
            }
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
}