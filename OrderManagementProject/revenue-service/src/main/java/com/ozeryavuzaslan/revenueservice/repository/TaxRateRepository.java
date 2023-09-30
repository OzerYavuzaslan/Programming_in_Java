package com.ozeryavuzaslan.revenueservice.repository;

import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.revenueservice.model.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {
    Optional<List<TaxRate>> findByIdIn(List<Long> taxRateDTOList);
    Optional<TaxRate> findByYearAndMonthAndTaxRateType(int taxYear, int taxMonth, TaxRateType taxRateType);
}
