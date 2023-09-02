package com.ozeryavuzaslan.stockservice.controller;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.service.StockService;
import com.ozeryavuzaslan.stockservice.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.ozeryavuzaslan.basedomains.util.Constants.STOCK_GET_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockController {
    private final ModelMapper modelMapper;
    private final StockService stockService;
    private final CustomLocation customLocation;

    @PostMapping("/stocks")
    public ResponseEntity<StockDTO> insertStock(@Valid @RequestBody StockWithoutUUIDDTO stockWithoutUUIDDTO){
        return ResponseEntity.created(customLocation.getURILocation(STOCK_GET_ENDPOINT,
                        stockService
                                .saveOrUpdateStock(stockWithoutUUIDDTO)
                                .getId()))
                .build();
    }

    @PutMapping("/stocks/updateStocks")
    public ResponseEntity<StockDTO> updateStock(@RequestParam UUID productCode,
                                                @Valid @RequestBody StockWithoutUUIDDTO stockWithoutUUIDDTO){
        return ResponseEntity.ok(stockService.saveOrUpdateStock(stockWithoutUUIDDTO));
    }

    @GetMapping("/stocks/{productCode}")
    public ResponseEntity<StockDTO> getStock(@PathVariable UUID productCode){
        return ResponseEntity.ok(stockService.getByProductCode(productCode));
    }

    @GetMapping("/stocks/getByProductName/{productName}")
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

    @DeleteMapping("/stocks")
    public ResponseEntity<String> deleteStock(@RequestParam UUID productCode){
        stockService.deleteStockByProductName(productCode);
        return new ResponseEntity<>(productCode + " has been deleted.", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/stocks/modify")
    public ResponseEntity<StockDTO> modifyProductQuantity(@RequestParam UUID productCode,
                                                          @RequestParam int quantityAmount){
        return ResponseEntity.ok(stockService.decreaseStockQuantity(productCode, quantityAmount));
    }
}