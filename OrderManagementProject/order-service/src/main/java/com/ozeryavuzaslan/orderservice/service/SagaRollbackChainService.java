package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import feign.Response;

import java.util.List;

public interface SagaRollbackChainService {
    void checkResponseAndBeginRollbackPhase1IfFailed(int statusCode, OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList, Response response) throws Exception;
    void checkResponseAndBeginRollbackPhase2IfFailed(int statusCode, OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList, Response response) throws Exception;
    void rollbackChainPhase3(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
    int beginRollbackChainPhase1(List<ReservedStockDTO> reservedStockDTOList);
    int beginRollbackChainPhase2(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
    int beginRollbackChainPhase3(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
    void beginOrderCancellation(OrderDTO orderDTO, Response response) throws Exception;
}