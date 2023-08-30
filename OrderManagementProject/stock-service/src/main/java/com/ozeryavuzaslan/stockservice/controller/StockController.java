package com.ozeryavuzaslan.stockservice.controller;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.stockservice.service.StockService;
import com.ozeryavuzaslan.stockservice.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ozeryavuzaslan.stockservice.util.Constants.STOCK_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockController {
    private final CustomLocation customLocation;
    private final StockService stockService;

    @PostMapping("/stocks")
    public ResponseEntity<StockDTO> insertOrUpdateStock(@Valid @RequestBody StockDTO stockDTO){
        return ResponseEntity.created(customLocation.getURILocation(STOCK_ENDPOINT,
                        stockService
                                .saveOrUpdateStock(stockDTO)
                                .getProductName()))
                .build();
    }

    @GetMapping("/stocks")
    public ResponseEntity<StockDTO> getStocks(@PathVariable String productName){
        return ResponseEntity.ok(stockService.getByProductName(productName));
    }
}