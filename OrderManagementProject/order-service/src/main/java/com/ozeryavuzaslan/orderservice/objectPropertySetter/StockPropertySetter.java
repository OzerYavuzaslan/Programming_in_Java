package com.ozeryavuzaslan.orderservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;

import java.util.List;

public interface StockPropertySetter {
    List<ReservedStockDTO> setSomeProperties(OrderDTO orderDTO);
    List<StockDTO> setSomeProperties(List<ReservedStockDTO> reservedStockDTOList);
}
