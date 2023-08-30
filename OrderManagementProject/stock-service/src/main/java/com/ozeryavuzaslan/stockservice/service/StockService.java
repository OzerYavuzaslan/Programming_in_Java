package com.ozeryavuzaslan.stockservice.service;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;

public interface StockService {
    StockDTO saveOrUpdateStock(StockDTO stockDTO);
    StockDTO getByProductName(String productName);
}