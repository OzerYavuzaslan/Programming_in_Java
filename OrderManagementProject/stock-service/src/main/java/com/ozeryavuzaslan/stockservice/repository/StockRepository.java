package com.ozeryavuzaslan.stockservice.repository;

import com.ozeryavuzaslan.stockservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductName(String productName);
    Optional<Stock> deleteByProductName(String productName);
}
