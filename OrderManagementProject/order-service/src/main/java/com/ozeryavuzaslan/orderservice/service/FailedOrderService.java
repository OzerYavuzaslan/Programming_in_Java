package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;

import java.util.List;

public interface FailedOrderService {
    void insertFailedOrderAndRollbackPhase(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
    void insertFailedOrderAndRollbackPhase(List<ReservedStockDTO> reservedStockDTOList, OrderDTO orderDTO);
    void insertFailedOrderAndRollbackPhase(List<ReservedStockDTO> reservedStockDTOList);
}
