package com.ozeryavuzaslan.stockservice.controller;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.stockservice.service.ReservedStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class ReservedStockController {
    private final ReservedStockService reservedStockService;

    @PostMapping("/reserveProducts")
    public ResponseEntity<List<ReservedStockDTO>> reserveStock(@Valid @RequestBody List<ReservedStockDTO> reservedStockDTOList){
        return ResponseEntity.ok(reservedStockService.reserveStock(reservedStockDTOList));
    }
}
