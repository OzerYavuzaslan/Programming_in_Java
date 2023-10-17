package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import feign.Response;

import java.io.IOException;
import java.util.List;

public interface SagaRollbackChainService {
    void checkResponseAndBeginRollbackPhase1IfFailed(int statusCode, OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList, Response response) throws IOException;
    void checkResponseAndBeginRollbackPhase2IfFailed(int statusCode, OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList, Response response) throws IOException;
    void rollbackChainPhase3(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
}