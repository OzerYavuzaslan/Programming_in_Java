package com.ozeryavuzaslan.orderservice.client;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "stock-service")
public interface StockServiceClient {
    @PostMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${reserve.stock.reserve.products.endpoint}")
    Response reserveStock(@RequestBody List<ReservedStockDTO> reservedStockDTOList);

    @PutMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${stock.decrease.stocks.endpoint}")
    Response decreaseStocks(@RequestBody List<ReservedStockDTO> reservedStockDTOList);

    @PutMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${reserve.stock.rollback.reserved.stock.endpoint}")
    Response rollbackReservedStocks(List<ReservedStockDTO> reservedStockDTOList);
}