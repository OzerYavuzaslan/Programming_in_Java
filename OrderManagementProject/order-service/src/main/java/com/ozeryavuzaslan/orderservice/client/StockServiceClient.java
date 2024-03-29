package com.ozeryavuzaslan.orderservice.client;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "stock-service")
public interface StockServiceClient {
    @PostMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${reserve.stock.reserve.products.endpoint}")
    Response reserveStock(@RequestBody List<ReservedStockDTO> reservedStockDTOList);

    @PutMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${stock.decrease.stocks.endpoint}")
    Response decreaseStocks(@RequestBody List<ReservedStockDTO> reservedStockDTOList);

    @PutMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${reserve.stock.rollback.reserved.stock.endpoint}")
    Response rollbackReservedStocks(@RequestBody List<ReservedStockDTO> reservedStockDTOList);

    @PutMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${stock.rollback.decreased.stock.endpoint}")
    Response rollbackDecreasedStocks(@RequestBody List<StockDTO> stockDTOList);

    @PutMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${reserve.rollback.stock.and.reserved.stocks.by.order.id.endpoint}" + "${path.variable.order.id}")
    Response rollbackStocksAndReservedStocksByOrderID(@PathVariable long orderid);

    @GetMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${reserve.stock.get.by.order.id.endpoint}" + "${path.variable.order.id}")
    Response getReservedStocksByOrderID(@PathVariable long orderID);
}