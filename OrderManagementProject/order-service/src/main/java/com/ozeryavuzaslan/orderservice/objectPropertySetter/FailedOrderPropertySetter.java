package com.ozeryavuzaslan.orderservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;
import com.ozeryavuzaslan.orderservice.model.FailedOrder;

import java.util.List;

public interface FailedOrderPropertySetter {
    FailedOrderDTO setSomeProperties(List<ReservedStockDTO> reservedStockDTOList);
    void setSomeProperties(FailedOrder failedOrder, FailedOrderDTO failedOrderDTO);
    FailedOrderDTO setSomeProperties(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList);
}
