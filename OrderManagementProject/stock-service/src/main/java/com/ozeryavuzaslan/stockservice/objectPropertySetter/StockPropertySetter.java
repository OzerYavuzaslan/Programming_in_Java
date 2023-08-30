package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
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
}
