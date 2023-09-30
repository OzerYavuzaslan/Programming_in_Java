package com.ozeryavuzaslan.orderservice.client;

import com.ozeryavuzaslan.basedomains.dto.stocks.DecreaseStockQuantityDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.StockAim;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "STOCK-SERVICE")
public interface StockServiceClient {
    @GetMapping("${base.endpoint}" + "${stock.base.endpoint}")
    List<StockDTO> getAllStocks(Pageable pageable);

    @PutMapping("${base.endpoint}" + "${stock.base.endpoint}" + "${stock.modify.or.get.specific.product.list}")
    List<StockDTO> modifyorGetProductList(@RequestBody List<DecreaseStockQuantityDTO> decreaseStockQuantityDTOList,
                                     @PathVariable("stockAim") StockAim stockAim);
}