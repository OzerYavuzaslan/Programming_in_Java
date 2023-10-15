package com.ozeryavuzaslan.orderservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.util.HandledHTTPExceptions;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;
import com.ozeryavuzaslan.orderservice.model.FailedOrder;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.FailedOrderPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.FailedOrderRepository;
import com.ozeryavuzaslan.orderservice.service.BeginSagaRollbackChain;
import com.ozeryavuzaslan.orderservice.service.RedirectAndFallbackHandler;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Service
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BeginSagaRollbackChainImpl implements BeginSagaRollbackChain {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final FailedOrderRepository failedOrderRepository;
    private final FailedOrderPropertySetter failedOrderPropertySetter;
    private final RedirectAndFallbackHandler redirectAndFallbackHandler;

    @Override
    public void beginRollbackFromReservedStocksPhase1(List<ReservedStockDTO> reservedStockDTOList) {
        try (Response responseRollbackReservedStocks = redirectAndFallbackHandler.redirectRollbackReservedStocks(reservedStockDTOList)) {
            int statusCode = responseRollbackReservedStocks.status();

            if (HandledHTTPExceptions.checkKnownException(statusCode)) {
                FailedOrderDTO failedOrderDTO = failedOrderPropertySetter.setSomeProperties(reservedStockDTOList);
                failedOrderRepository.save(modelMapper.map(failedOrderDTO, FailedOrder.class));
            }
        }
    }
}
