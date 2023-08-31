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

    public void setSomeProperties(StockDTO stockDTO, boolean isInsert, boolean isCategoryPresent){
        if (isInsert)
            stockDTO.setAddDate(LocalDateTime.now());

        stockDTO.setUpdateDate(LocalDateTime.now());
        categoryPropertySetter.setSomeProperties(stockDTO.getCategory(), isInsert, isCategoryPresent);
    }

    public void setSomeProperties(Stock stock, StockDTO stockDTO){
        stockDTO.setAddDate(stock.getAddDate());
        stockDTO.setId(stock.getId());
        categoryPropertySetter.setSomeProperties(stock.getCategory(), stockDTO.getCategory());
    }
}
