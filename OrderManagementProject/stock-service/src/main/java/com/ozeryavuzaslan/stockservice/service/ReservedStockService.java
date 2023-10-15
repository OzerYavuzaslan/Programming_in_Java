package com.ozeryavuzaslan.stockservice.service;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;

import java.util.List;

public interface ReservedStockService {
    List<ReservedStockDTO> reserveStock(List<ReservedStockDTO> reservedStockDTOList);
    List<ReservedStockDTO> rollbackReserveStock(List<ReservedStockDTO> reservedStockDTOList);
}
