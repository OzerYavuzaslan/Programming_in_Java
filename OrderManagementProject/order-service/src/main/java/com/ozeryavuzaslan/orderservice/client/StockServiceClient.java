package com.ozeryavuzaslan.orderservice.client;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "stock-service")
public interface StockServiceClient {
    @GetMapping("${base.endpoint}" + "${stock.base.endpoint}")
    List<StockDTO> getAllStocks(Pageable pageable);

    @PostMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${reserve.stock.reserve.products.endpoint}")
    List<ReservedStockDTO> reserveStock(@RequestBody List<ReservedStockDTO> reservedStockDTOList);

    @PutMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${stock.decrease.stocks.endpoint}")
    List<ReservedStockDTO> decreaseStocks(List<ReservedStockDTO> reservedStockDTOList);
}