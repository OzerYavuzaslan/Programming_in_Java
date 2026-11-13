package com.omniflow.order.infra.persistence.repository;

import com.omniflow.order.infra.persistence.entity.OrderStatusHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link OrderStatusHistoryJpaEntity}.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public interface SpringDataOrderStatusHistoryJpaRepository extends JpaRepository<OrderStatusHistoryJpaEntity, UUID> {
    List<OrderStatusHistoryJpaEntity> findByOrderIdOrderByChangedAtAsc(UUID orderId);
}