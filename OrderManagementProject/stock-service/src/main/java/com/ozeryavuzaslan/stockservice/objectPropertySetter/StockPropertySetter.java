package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.stockservice.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StockPropertySetter {
    private final CategoryPropertySetter categoryPropertySetter;

    public void setSomeProperties(StockDTO stockDTO, boolean isInsert, boolean isCategoryNotPresent){
        if (isInsert) {
            stockDTO.setProductCode(UUID.randomUUID());
            stockDTO.setAddDate(LocalDateTime.now());
        }

        stockDTO.setUpdateDate(LocalDateTime.now());
        categoryPropertySetter.setSomeProperties(stockDTO.getCategory(), isInsert, isCategoryNotPresent);
    }

    public void setSomeProperties(Stock stock, StockDTO stockDTO){
        stockDTO.setAddDate(stock.getAddDate());
        stockDTO.setProductCode(stock.getProductCode());
        stockDTO.setId(stock.getId());
        categoryPropertySetter.setSomeProperties(stock.getCategory(), stockDTO.getCategory());
    }
}
