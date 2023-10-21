package com.ozeryavuzaslan.stockservice.controller;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.stockservice.service.ReservedStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class ReservedStockController {
    private final ReservedStockService reservedStockService;

    @PostMapping("/reserveProducts")
    public ResponseEntity<List<ReservedStockDTO>> reserveStock(@Valid @RequestBody List<ReservedStockDTO> reservedStockDTOList) {
        return ResponseEntity.ok(reservedStockService.reserveStock(reservedStockDTOList));
    }

    @PutMapping("/rollbackReservedStocks")
    public ResponseEntity<List<ReservedStockDTO>> rollbackReserveStock(@Valid @RequestBody List<ReservedStockDTO> reservedStockDTOList) {
        return ResponseEntity.ok(reservedStockService.rollbackReserveStock(reservedStockDTOList));
    }

    @PutMapping("/rollbackStocksAndReservedStocks/{orderid}")
    public ResponseEntity<List<ReservedStockDTO>> rollbackStocksAndReservedStocksByOrderID(@PathVariable long orderid) {
        return ResponseEntity.ok(reservedStockService.rollbackStocksAndReserveStocks(orderid));
    }

    @GetMapping("/getByOrderId/{orderid}")
    public ResponseEntity<List<ReservedStockDTO>> getByOrderID(@PathVariable long orderid) {
        return ResponseEntity.ok(reservedStockService.getByOrderID(orderid));
    }
}