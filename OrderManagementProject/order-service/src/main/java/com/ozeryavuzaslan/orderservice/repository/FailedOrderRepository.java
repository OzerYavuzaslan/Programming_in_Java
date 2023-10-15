package com.ozeryavuzaslan.orderservice.repository;

import com.ozeryavuzaslan.orderservice.model.FailedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedOrderRepository extends JpaRepository<FailedOrder, Long> {
}
