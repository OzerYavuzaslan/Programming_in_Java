package com.ozeryavuzaslan.orderservice.repository;

import com.ozeryavuzaslan.orderservice.model.FailedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FailedOrderRepository extends JpaRepository<FailedOrder, Long> {
    Optional<FailedOrder> findByOrderid(long orderid);
}
