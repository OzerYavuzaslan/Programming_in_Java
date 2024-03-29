package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackReason;

import java.util.List;

public interface FailedOrderService {
    void insertFailedOrderAndRollbackPhase(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
    void insertFailedOrderAndRollbackPhase(List<ReservedStockDTO> reservedStockDTOList, OrderDTO orderDTO);
    void insertFailedOrderAndRollbackPhase(List<ReservedStockDTO> reservedStockDTOList);
    void insertFailedOrderAndRollbackPhase(RollbackReason rollbackReason, OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
    void insertFailedOrderAndRollbackPhase(OrderDTO orderDTO);
    FailedOrderDTO getSpecificFailedOrder(long failedOrderID);
}
