package com.ozeryavuzaslan.stockservice.controller;

import com.ozeryavuzaslan.basedomains.dto.stocks.DecreaseStockQuantityDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.StockAim;
import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
import com.ozeryavuzaslan.stockservice.service.StockService;
import com.ozeryavuzaslan.stockservice.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class StockController {
    private final StockService stockService;
    private final CustomLocation customLocation;
    private final CacheManagementService cacheManagementService;

    @Value("${stock.get.by.id.endpoint}")
    private String stockGetEndpoint;

    @Value("${stock.cache.name}")
    private String stockCacheName;

    @PostMapping
    public ResponseEntity<StockDTO> insertStock(@Valid @RequestBody StockWithoutUUIDDTO stockWithoutUUIDDTO){
        return ResponseEntity.created(customLocation.getURILocation(stockGetEndpoint,
                        stockService
                                .saveOrUpdateStock(stockWithoutUUIDDTO)
                                .getId()))
                .build();
    }

    @PutMapping("/updateStocks")
    public ResponseEntity<StockDTO> updateStock(@Valid @RequestBody StockDTO stockDTO){
        return ResponseEntity.ok(stockService.updateStock(stockDTO));
    }

    @PutMapping("/product")
    public ResponseEntity<StockDTO> modifyProductQuantity(@RequestParam UUID productCode,
                                                          @RequestParam int quantityAmount){
        return ResponseEntity.ok(stockService.getSpecificStocks(productCode, quantityAmount));
    }

    @PutMapping("/modifyOrGet/specificProductList/{stockAim}")
    public ResponseEntity<List<StockDTO>> modifyProductListQuantity(@Valid @RequestBody List<DecreaseStockQuantityDTO> decreaseStockQuantityDTOList,
                                                                    @PathVariable StockAim stockAim){
        return ResponseEntity.ok(stockService.getSpecificStocks(decreaseStockQuantityDTOList, stockAim));
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<StockDTO> getStock(@PathVariable UUID productCode){
        return ResponseEntity.ok(stockService.getByProductCode(productCode));
    }

    @GetMapping("/getByProductName/{productName}")
    public ResponseEntity<StockDTO> getStock(@PathVariable String productName){
        return ResponseEntity.ok(stockService.getByProductName(productName));
    }

    @GetMapping("/getByProductId/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable long id){
        return ResponseEntity.ok(stockService.getByProductID(id));
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getStocks(@ParameterObject Pageable pageable){
        stockService.checkStockServiceCacheState();
        return ResponseEntity.ok(stockService.getStockList(pageable));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStock(@RequestParam UUID productCode){
        stockService.deleteStockByProductCode(productCode);
        return new ResponseEntity<>(productCode + " has been deleted.", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/clearCache")
    public ResponseEntity<String> clearCache(){
        cacheManagementService.clearCache(stockCacheName);
        return new ResponseEntity<>("Stock cache has been refreshed.", HttpStatus.OK);
    }
}