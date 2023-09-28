package com.ozeryavuzaslan.orderservice.repository;

import com.ozeryavuzaslan.orderservice.model.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {
}
