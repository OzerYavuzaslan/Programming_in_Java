package com.ozeryavuzaslan.orderservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;

import java.util.List;

public interface FailedOrderPropertySetter {
    FailedOrderDTO setSomeProperties(List<ReservedStockDTO> reservedStockDTOList);
}
