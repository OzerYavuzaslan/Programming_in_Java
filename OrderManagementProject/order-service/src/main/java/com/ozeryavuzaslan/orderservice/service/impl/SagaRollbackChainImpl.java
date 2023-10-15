package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.RefundRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.util.HandledHTTPExceptions;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;
import com.ozeryavuzaslan.orderservice.model.FailedOrder;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.FailedOrderPropertySetter;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.FailedOrderRepository;
import com.ozeryavuzaslan.orderservice.service.SagaRollbackChain;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Service
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SagaRollbackChainImpl implements SagaRollbackChain {
    private final ModelMapper modelMapper;
    private final PaymentPropertySetter paymentPropertySetter;
    private final FailedOrderRepository failedOrderRepository;
    private final FailedOrderPropertySetter failedOrderPropertySetter;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertFailedOrderAndRollbackPhase(List<ReservedStockDTO> reservedStockDTOList){
        FailedOrderDTO failedOrderDTO = failedOrderPropertySetter.setSomeProperties(reservedStockDTOList);
        FailedOrder failedOrder = modelMapper.map(failedOrderDTO, FailedOrder.class);
        failedOrderPropertySetter.setSomeProperties(failedOrder, failedOrderDTO);
        failedOrderRepository.save(failedOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertFailedOrderAndRollbackPhase(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList){
        FailedOrderDTO failedOrderDTO = failedOrderPropertySetter.setSomeProperties(orderDTO, reservedStockDTOList);
        FailedOrder failedOrder = modelMapper.map(failedOrderDTO, FailedOrder.class);
        failedOrderPropertySetter.setSomeProperties(failedOrder, failedOrderDTO);
        failedOrderRepository.save(failedOrder);
    }
}
