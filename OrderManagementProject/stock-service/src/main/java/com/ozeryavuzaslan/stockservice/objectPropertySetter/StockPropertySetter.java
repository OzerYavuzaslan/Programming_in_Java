package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.model.Stock;

public interface StockPropertySetter {
    void setSomeProperties(StockWithoutUUIDDTO stockWithoutUUIDDTO, boolean isInsert, boolean isCategoryPresent);
    void setSomeProperties(Stock stock, StockWithoutUUIDDTO stockWithoutUUIDDTO);
}
