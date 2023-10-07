package com.ozeryavuzaslan.stockservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.model.ReservedStock;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.CategoryPropertySetter;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.StockPropertySetter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public void setSomeProperties(List<ReservedStock> reservedStockList, List<ReservedStockDTO> reservedStockDTOList) {
        for (int i = 0; i < reservedStockList.size(); i++){
            reservedStockDTOList.get(i).setReserveType(reservedStockList.get(i).getReserveType());
            reservedStockDTOList.get(i).setOrderid(reservedStockList.get(i).getOrderid());
            reservedStockDTOList.get(i).setId(reservedStockList.get(i).getId());
            reservedStockDTOList.get(i).setQuantity(reservedStockList.get(i).getQuantity());

            reservedStockDTOList.get(i).getStock().setId(reservedStockList.get(i).getStock().getId());
            reservedStockDTOList.get(i).getStock().setProductCode(reservedStockList.get(i).getStock().getProductCode());
            reservedStockDTOList.get(i).getStock().setProductName(reservedStockList.get(i).getStock().getProductName());
            reservedStockDTOList.get(i).getStock().setBrandName(reservedStockList.get(i).getStock().getBrandName());
            reservedStockDTOList.get(i).getStock().setBrandCompanyEmail(reservedStockList.get(i).getStock().getBrandCompanyEmail());
            reservedStockDTOList.get(i).getStock().setQuantity(reservedStockList.get(i).getStock().getQuantity());
            reservedStockDTOList.get(i).getStock().setPrice(reservedStockList.get(i).getStock().getPrice());
            reservedStockDTOList.get(i).getStock().setDiscountAmount(reservedStockList.get(i).getStock().getDiscountAmount());
            reservedStockDTOList.get(i).getStock().setDiscountPercentage(reservedStockList.get(i).getStock().getDiscountPercentage());
            reservedStockDTOList.get(i).getStock().setAddDate(reservedStockList.get(i).getStock().getAddDate());
            reservedStockDTOList.get(i).getStock().setUpdateDate(reservedStockList.get(i).getStock().getUpdateDate());
            reservedStockDTOList.get(i).getStock().setDiscountEndDate(reservedStockList.get(i).getStock().getDiscountEndDate());

            reservedStockDTOList.get(i).getStock().getCategory().setId((reservedStockList.get(i).getStock().getCategory().getId()));
            reservedStockDTOList.get(i).getStock().getCategory().setCategoryCode((reservedStockList.get(i).getStock().getCategory().getCategoryCode()));
            reservedStockDTOList.get(i).getStock().getCategory().setName((reservedStockList.get(i).getStock().getCategory().getName()));
            reservedStockDTOList.get(i).getStock().getCategory().setAddDate((reservedStockList.get(i).getStock().getCategory().getAddDate()));
            reservedStockDTOList.get(i).getStock().getCategory().setUpdateDate((reservedStockList.get(i).getStock().getCategory().getUpdateDate()));
        }
    }

    private double getDiscountAmount(StockWithoutUUIDDTO stockWithoutUUIDDTO){
        return stockWithoutUUIDDTO.getPrice() * stockWithoutUUIDDTO.getDiscountPercentage() / 100;
    }
}
