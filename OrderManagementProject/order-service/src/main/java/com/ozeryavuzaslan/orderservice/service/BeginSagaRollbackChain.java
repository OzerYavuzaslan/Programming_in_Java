package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;

import java.util.List;

public interface BeginSagaRollbackChain {
    int beginRollbackFromReservedStocksPhase1(List<ReservedStockDTO> reservedStockDTOList);
    void insertFailedOrderAndRollbackPhase(List<ReservedStockDTO> reservedStockDTOList);
    int beginRollbackFromReservedStocksPhase2(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
    void insertFailedOrderAndRollbackPhase(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
}
