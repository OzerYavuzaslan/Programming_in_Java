package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.model.ReservedStock;
import com.ozeryavuzaslan.stockservice.model.Stock;

import java.util.List;

public interface StockPropertySetter {
    void setSomeProperties(StockWithoutUUIDDTO stockWithoutUUIDDTO, boolean isInsert, boolean isCategoryPresent);
    void setSomeProperties(Stock stock, StockWithoutUUIDDTO stockWithoutUUIDDTO);
    void setSomeProperties(List<ReservedStock> reservedStockList, List<ReservedStockDTO> reservedStockDTOList);
}
