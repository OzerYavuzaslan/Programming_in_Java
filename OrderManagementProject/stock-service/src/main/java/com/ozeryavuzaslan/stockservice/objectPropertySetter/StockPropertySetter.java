package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.stockservice.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class StockPropertySetter {
    private final CategoryPropertySetter categoryPropertySetter;

    public StockDTO setDates(StockDTO stockDTO, boolean isInsert){
        if (isInsert)
            stockDTO.setAddDate(LocalDateTime.now());

        stockDTO.setUpdateDate(LocalDateTime.now());
        stockDTO.setCategory(categoryPropertySetter.setDates(stockDTO.getCategory(), isInsert));

        return stockDTO;
    }

    public Stock setDates(Stock stock){
        stock.setUpdateDate(LocalDateTime.now());
        stock.setCategory(categoryPropertySetter.setDates(stock.getCategory()));

        return stock;
    }
}
