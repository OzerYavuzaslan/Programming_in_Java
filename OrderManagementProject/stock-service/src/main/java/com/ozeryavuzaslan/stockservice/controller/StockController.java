package com.ozeryavuzaslan.stockservice.controller;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.stockservice.service.StockService;
import com.ozeryavuzaslan.stockservice.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ozeryavuzaslan.basedomains.util.Constants.STOCK_ENDPOINT;

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
                                .getId()))
                .build();
    }

    @GetMapping("/stocks/{productName}")
    public ResponseEntity<StockDTO> getStock(@PathVariable String productName){
        return ResponseEntity.ok(stockService.getByProductName(productName));
    }

    @GetMapping("/stocks/getByProductId/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable long id){
        return ResponseEntity.ok(stockService.getByProductID(id));
    }

    @GetMapping("/stocks")
    public ResponseEntity<List<StockDTO>> getStocks(){
        return ResponseEntity.ok(stockService.getStockList());
    }

    @DeleteMapping("stocks")
    public ResponseEntity<String> deleteStock(@RequestParam String productName){
        stockService.deleteStockByProductName(productName);
        return new ResponseEntity<>(productName + " has been deleted.", HttpStatus.NO_CONTENT);
    }

    @PutMapping("stocks")
    public ResponseEntity<StockDTO> modifyProductQuantity(@RequestParam String productName,
                                                          @RequestParam int quantityAmount){
        return ResponseEntity.ok(stockService.decreaseStockQuantity(productName, quantityAmount));
    }
}