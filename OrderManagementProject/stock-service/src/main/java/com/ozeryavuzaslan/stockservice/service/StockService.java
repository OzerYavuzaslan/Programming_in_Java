package com.ozeryavuzaslan.stockservice.service;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.StockWithoutUUIDDTO;

import java.util.List;
import java.util.UUID;

public interface StockService {
    StockDTO saveOrUpdateStock(StockWithoutUUIDDTO stockWithoutUUIDDTO);
    StockDTO updateStock(UUID productCode, StockWithoutUUIDDTO stockWithoutUUIDDTO);
    StockDTO getByProductCode(UUID productCode);
    StockDTO getByProductName(String productName);
    StockDTO getByProductID(long productID);
    List<StockDTO> getStockList();
    void deleteStockByProductName(UUID productCode);
    StockDTO decreaseStockQuantity(UUID productCode, int quantityAmount);
}