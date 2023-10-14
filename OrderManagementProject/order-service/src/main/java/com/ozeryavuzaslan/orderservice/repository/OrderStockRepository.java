package com.ozeryavuzaslan.orderservice.repository;

import com.ozeryavuzaslan.orderservice.model.OrderStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStockRepository extends JpaRepository<OrderStock, Long> {
}
