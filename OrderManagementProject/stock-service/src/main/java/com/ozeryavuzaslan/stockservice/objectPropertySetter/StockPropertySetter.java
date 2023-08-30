package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StockPropertySetter {
    public StockDTO convert(StockDTO stockDTO, boolean isInsert){
        if (isInsert)
            stockDTO.setAddDate(LocalDateTime.now());

        stockDTO.setUpdateDate(LocalDateTime.now());

        return stockDTO;
    }
}
