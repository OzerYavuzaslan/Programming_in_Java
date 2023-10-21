package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.stockservice.model.ReservedStock;
import com.ozeryavuzaslan.stockservice.model.Stock;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReservedStockPropertySetter {
    Map<UUID, ReservedStock> setSomeProperties(List<ReservedStock> reservedStockList);
    void setSomeProperties(List<Stock> stockList, List<ReservedStock> reservedStockList);
}
