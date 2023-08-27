package com.ozeryavuzaslan.stockservice.repository;

import com.ozeryavuzaslan.stockservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
