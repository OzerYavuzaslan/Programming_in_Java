package com.ozeryavuzaslan.stockservice.service;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;

import java.util.List;

public interface StockService {
    StockDTO saveOrUpdateStock(StockDTO stockDTO);
    StockDTO getByProductName(String productName);
    StockDTO getByProductID(long productID);
    List<StockDTO> getStockList();
    void deleteStockByProductName(String productName);
    StockDTO decreaseStockQuantity(String productName, int quantityAmount);
}