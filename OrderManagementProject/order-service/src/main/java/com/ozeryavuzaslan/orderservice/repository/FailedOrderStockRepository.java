package com.ozeryavuzaslan.orderservice.repository;

import com.ozeryavuzaslan.orderservice.model.FailedOrderStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedOrderStockRepository extends JpaRepository<FailedOrderStock, Long> {
}
