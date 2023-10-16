package com.ozeryavuzaslan.stockservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithIgnoredUUID;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.CategoryPropertySetter;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.StockPropertySetter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StockPropertySetterImpl implements StockPropertySetter{
    private final CategoryPropertySetter categoryPropertySetter;

    @Override
    public void setSomeProperties(StockWithIgnoredUUID stockWithIgnoredUUID, boolean isInsert, boolean isCategoryPresent){
        if (isInsert) {
            stockWithIgnoredUUID.setProductCode(UUID.randomUUID());
            stockWithIgnoredUUID.setAddDate(LocalDateTime.now());
        }

        if (stockWithIgnoredUUID.getDiscountPercentage() > 0.0D)
            stockWithIgnoredUUID.setDiscountAmount(getDiscountAmount(stockWithIgnoredUUID));

        stockWithIgnoredUUID.setUpdateDate(LocalDateTime.now());
        categoryPropertySetter.setSomeProperties(stockWithIgnoredUUID.getCategory(), isInsert, isCategoryPresent);
    }

    @Override
    public void setSomeProperties(Stock stock, StockWithIgnoredUUID stockWithIgnoredUUID){
        stockWithIgnoredUUID.setAddDate(stock.getAddDate());
        stockWithIgnoredUUID.setProductCode(stock.getProductCode());
        stockWithIgnoredUUID.setId(stock.getId());

        if (stockWithIgnoredUUID.getDiscountPercentage() > 0.0D)
            stockWithIgnoredUUID.setDiscountAmount(getDiscountAmount(stockWithIgnoredUUID));

        categoryPropertySetter.setSomeProperties(stock.getCategory(), stockWithIgnoredUUID.getCategory());
    }

    private double getDiscountAmount(StockWithIgnoredUUID stockWithIgnoredUUID){
        return stockWithIgnoredUUID.getPrice() * stockWithIgnoredUUID.getDiscountPercentage() / 100;
    }
}
