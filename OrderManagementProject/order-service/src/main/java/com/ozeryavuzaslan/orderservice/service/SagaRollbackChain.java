package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;

import java.util.List;

public interface SagaRollbackChain {
    int beginRollbackChainPhase1(List<ReservedStockDTO> reservedStockDTOList);
    int beginRollbackChainPhase2(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
    int beginRollbackChainPhase3(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
}