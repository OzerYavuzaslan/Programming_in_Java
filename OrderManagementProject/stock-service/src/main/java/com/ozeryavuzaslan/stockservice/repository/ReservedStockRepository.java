package com.ozeryavuzaslan.stockservice.repository;

import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import com.ozeryavuzaslan.stockservice.model.ReservedStock;
import com.ozeryavuzaslan.stockservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservedStockRepository extends JpaRepository<ReservedStock, Long> {
    Optional<List<ReservedStock>> findByStockInAndReserveTypeOrderByStock_ProductCodeAsc(List<Stock> stocks, ReserveType reserveType);
    List<ReservedStock> findByIdInAndReserveTypeOrderByIdAsc(List<Long> reservedStockIDList, ReserveType reserveType);
}
