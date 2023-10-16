package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.RefundRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.util.HandledHTTPExceptions;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import com.ozeryavuzaslan.orderservice.service.SagaRollbackChain;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Service
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SagaRollbackChainImpl implements SagaRollbackChain {
    private final PaymentPropertySetter paymentPropertySetter;
    private final RedirectAndFallbackHandler redirectAndFallbackHandler;
    private final RefundRequestDTOForPaymentService refundRequestDTOForPaymentService;

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

            if (HandledHTTPExceptions.checkKnownException(statusCode))
                return statusCode;

            return beginRollbackChainPhase1(reservedStockDTOList);
        }
    }

    @Override
    public int beginRollbackChainPhase3(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList) {
        return 0;
    }
}
