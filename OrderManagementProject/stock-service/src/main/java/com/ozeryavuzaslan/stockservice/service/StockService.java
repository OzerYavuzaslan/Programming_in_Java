package com.ozeryavuzaslan.stockservice.service;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithoutUUIDDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface StockService {
    StockDTO saveOrUpdateStock(StockWithoutUUIDDTO stockWithoutUUIDDTO);
    StockDTO updateStock(StockDTO stockDTO);
    List<ReservedStockDTO> decreaseStock(List<ReservedStockDTO> reservedStockDTOList);
    StockDTO getByProductCode(UUID productCode);
    StockDTO getByProductName(String productName);
    StockDTO getByProductID(long productID);
    List<StockDTO> getStockList(Pageable pageable);
    List<StockDTO> getStockList();
    void deleteStockByProductCode(UUID productCode);
    void checkStockServiceCacheState();
}