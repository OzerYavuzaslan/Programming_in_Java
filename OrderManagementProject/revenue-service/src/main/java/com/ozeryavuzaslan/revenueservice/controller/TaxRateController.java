package com.ozeryavuzaslan.revenueservice.controller;

import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
import com.ozeryavuzaslan.revenueservice.service.TaxRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/revenues")
public class TaxRateController {
    private final TaxRateService taxRateService;
    private final CacheManagementService cacheManagementService;

    @Value("${tax.rate.cache.name}")
    private String taxRateCacheName;

    @PostMapping("/taxRate")
    public ResponseEntity<TaxRateDTO> insertTaxRate(@Valid @RequestBody TaxRateDTO taxRateDTO){
        return ResponseEntity.ok(taxRateService.insertTaxRate(taxRateDTO));
    }

    @PostMapping("/taxRate/bulkInsert")
    public ResponseEntity<List<TaxRateDTO>> taxRateBulkInsert(@Valid @RequestBody List<TaxRateDTO> taxRateDTOList){
        return ResponseEntity.ok(taxRateService.taxRateBulkInsert(taxRateDTOList));
    }

    @PutMapping("/taxRate")
    public ResponseEntity<TaxRateDTO> updateTaxRate(@Valid @RequestBody TaxRateDTO taxRateDTO){
        return ResponseEntity.ok(taxRateService.updateTaxRate(taxRateDTO));
    }

    @PutMapping("/taxRate/bulkUpdate")
    public ResponseEntity<List<TaxRateDTO>> taxRateBulkUpdate(@Valid @RequestBody List<TaxRateDTO> taxRateDTOList){
        return ResponseEntity.ok(taxRateService.taxRateBulkUpdate(taxRateDTOList));
    }

    @DeleteMapping("/taxRate")
    public ResponseEntity<TaxRateDTO> deleteSpecificTaxRate(@Valid @RequestBody TaxRateDTO taxRateDTO){
        return ResponseEntity.ok(taxRateService.deleteSpecificTaxRate(taxRateDTO));
    }

    @GetMapping("/taxRate")
    public ResponseEntity<List<TaxRateDTO>> getTaxRates(@ParameterObject Pageable pageable){
        taxRateService.checkTaxRateServiceCacheState();
        return ResponseEntity.ok(taxRateService.getAllTaxRates(pageable));
    }

    @GetMapping("/taxRate/getSpecificTaxRate")
    public ResponseEntity<TaxRateDTO> getSpecificTaxRate(@RequestParam int taxYear,
                                                          @RequestParam int taxMonth,
                                                          @RequestParam TaxRateType taxRateType){
        return ResponseEntity.ok(taxRateService.getTaxRate(taxYear, taxMonth, taxRateType));
    }

    @PatchMapping("/taxRate/clearCache")
    public ResponseEntity<String> clearCache(){
        cacheManagementService.clearCache(taxRateCacheName);
        return new ResponseEntity<>("Taxes cache has been refreshed.", HttpStatus.OK);
    }
}
