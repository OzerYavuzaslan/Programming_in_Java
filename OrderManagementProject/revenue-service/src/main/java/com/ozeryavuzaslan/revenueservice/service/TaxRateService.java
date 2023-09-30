package com.ozeryavuzaslan.revenueservice.service;

import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaxRateService {
    TaxRateDTO insertTaxRate(TaxRateDTO taxRateDTO);
    List<TaxRateDTO> taxRateBulkInsert(List<TaxRateDTO> taxRateDTOList);
    TaxRateDTO updateTaxRate(TaxRateDTO taxRateDTO);
    List<TaxRateDTO> taxRateBulkUpdate(List<TaxRateDTO> taxRateDTOList);
    TaxRateDTO deleteSpecificTaxRate(TaxRateDTO taxRateDTO);
    List<TaxRateDTO> getAllTaxRates(Pageable pageable);
    TaxRateDTO getTaxRate(int taxYear, int taxMonth, TaxRateType taxRateType);
    void checkTaxRateServiceCacheState();
}
