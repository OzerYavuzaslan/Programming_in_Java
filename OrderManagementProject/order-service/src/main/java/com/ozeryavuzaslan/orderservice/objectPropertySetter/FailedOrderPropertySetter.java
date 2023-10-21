package com.ozeryavuzaslan.orderservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;
import com.ozeryavuzaslan.orderservice.model.FailedOrder;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackPhase;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackReason;

import java.util.List;

public interface FailedOrderPropertySetter {
    FailedOrderDTO setSomeProperties(List<ReservedStockDTO> reservedStockDTOList, RollbackPhase rollbackPhase, RollbackReason rollbackReason);
    void setSomeProperties(FailedOrder failedOrder, FailedOrderDTO failedOrderDTO);
    FailedOrderDTO setSomeProperties(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList, RollbackPhase rollbackPhase, RollbackReason rollbackReason);
    FailedOrderDTO setSomeProperties(OrderDTO orderDTO, RollbackPhase rollbackPhase, RollbackReason rollbackReason);
}
