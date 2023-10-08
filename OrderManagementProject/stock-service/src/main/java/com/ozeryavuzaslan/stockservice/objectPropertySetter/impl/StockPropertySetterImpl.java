package com.ozeryavuzaslan.stockservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.CategoryPropertySetter;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.StockPropertySetter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StockPropertySetterImpl implements StockPropertySetter{
    private final ModelMapper modelMapper;
    private final CategoryPropertySetter categoryPropertySetter;

    @Override
    public void setSomeProperties(StockWithoutUUIDDTO stockWithoutUUIDDTO, boolean isInsert, boolean isCategoryPresent){
        if (isInsert) {
            stockWithoutUUIDDTO.setProductCode(UUID.randomUUID());
            stockWithoutUUIDDTO.setAddDate(LocalDateTime.now());
        }

        if (stockWithoutUUIDDTO.getDiscountPercentage() > 0.0D)
            stockWithoutUUIDDTO.setDiscountAmount(getDiscountAmount(stockWithoutUUIDDTO));

        stockWithoutUUIDDTO.setUpdateDate(LocalDateTime.now());
        categoryPropertySetter.setSomeProperties(stockWithoutUUIDDTO.getCategory(), isInsert, isCategoryPresent);
    }

    @Override
    public void setSomeProperties(Stock stock, StockWithoutUUIDDTO stockWithoutUUIDDTO){
        stockWithoutUUIDDTO.setAddDate(stock.getAddDate());
        stockWithoutUUIDDTO.setProductCode(stock.getProductCode());
        stockWithoutUUIDDTO.setId(stock.getId());

        if (stockWithoutUUIDDTO.getDiscountPercentage() > 0.0D)
            stockWithoutUUIDDTO.setDiscountAmount(getDiscountAmount(stockWithoutUUIDDTO));

        categoryPropertySetter.setSomeProperties(stock.getCategory(), stockWithoutUUIDDTO.getCategory());
    }

    private double getDiscountAmount(StockWithoutUUIDDTO stockWithoutUUIDDTO){
        return stockWithoutUUIDDTO.getPrice() * stockWithoutUUIDDTO.getDiscountPercentage() / 100;
    }
}
