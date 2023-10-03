package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;

import java.util.List;

public interface PriceCalculationService {
    void calculateOrderPrice(List<ReservedStockDTO> reservedStockDTOList, TaxRateDTO taxRateDTO, OrderDTO orderDTO);
}
