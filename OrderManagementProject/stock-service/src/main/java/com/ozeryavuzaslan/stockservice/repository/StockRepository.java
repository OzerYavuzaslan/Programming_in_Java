package com.ozeryavuzaslan.stockservice.repository;

import com.ozeryavuzaslan.stockservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductCode(UUID productCode);
    Optional<Stock> findByProductName(String productName);
    Optional<List<Stock>> findByProductCodeInOrderByProductCodeAsc(List<UUID> productCodeList);
    List<Stock> findByIdInOrderByIdAsc(List<Long> stockIDList);
}
