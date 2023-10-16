package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithIgnoredUUID;
import com.ozeryavuzaslan.stockservice.model.Stock;

public interface StockPropertySetter {
    void setSomeProperties(StockWithIgnoredUUID stockWithIgnoredUUID, boolean isInsert, boolean isCategoryPresent);
    void setSomeProperties(Stock stock, StockWithIgnoredUUID stockWithIgnoredUUID);
}
