package com.omniflow.common.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Integration event emitted when a new order is created.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderCreatedEvent(UUID eventId,
        Instant occurredAt,
        int schemaVersion,
        String tenantId,
        UUID orderId,
        UUID customerId,
        BigDecimal totalAmount,
        String currency) implements OrderIntegrationEvent {
    public static final String EVENT_TYPE = "ORDER_CREATED";

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}