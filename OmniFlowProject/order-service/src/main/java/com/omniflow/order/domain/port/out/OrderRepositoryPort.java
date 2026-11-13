package com.omniflow.order.domain.port.out;

import com.omniflow.order.domain.model.OrderAggregate;
import com.omniflow.order.domain.model.OrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Outbound port for order persistence operations.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public interface OrderRepositoryPort {
    OrderAggregate save(OrderAggregate order);
    Optional<OrderAggregate> findById(UUID orderId);
    Optional<OrderAggregate> findByTenantIdAndIdempotencyKey(String tenantId, String idempotencyKey);
    List<OrderSummaryDTO> findRecentSummaries(String tenantId, OrderStatus status, Instant createdBefore, int limit);
}