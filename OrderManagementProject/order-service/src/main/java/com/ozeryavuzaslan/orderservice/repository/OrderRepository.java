package com.ozeryavuzaslan.orderservice.repository;

import com.ozeryavuzaslan.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}