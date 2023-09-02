package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.model.Stock;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StockPropertySetter {
    private final ModelMapper modelMapper;
    private final CategoryPropertySetter categoryPropertySetter;

    public void setSomeProperties(StockWithoutUUIDDTO stockWithoutUUIDDTO, boolean isInsert, boolean isCategoryPresent){
        if (isInsert) {
            stockWithoutUUIDDTO.setProductCode(UUID.randomUUID());
            stockWithoutUUIDDTO.setAddDate(LocalDateTime.now());
        }

        stockWithoutUUIDDTO.setUpdateDate(LocalDateTime.now());
        categoryPropertySetter.setSomeProperties(stockWithoutUUIDDTO.getCategory(), isInsert, isCategoryPresent);
    }

    public void setSomeProperties(Stock stock, StockWithoutUUIDDTO stockWithoutUUIDDTO){
        stockWithoutUUIDDTO.setAddDate(stock.getAddDate());
        stockWithoutUUIDDTO.setProductCode(stock.getProductCode());
        stockWithoutUUIDDTO.setId(stock.getId());
        categoryPropertySetter.setSomeProperties(stock.getCategory(), stockWithoutUUIDDTO.getCategory());
    }
}
