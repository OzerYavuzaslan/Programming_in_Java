package com.omniflow.common.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Integration event emitted when an order is cancelled.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderCancelledEvent(
        UUID eventId,
        Instant occurredAt,
        int schemaVersion,
        String tenantId,
        UUID orderId,
        String reason) implements OrderIntegrationEvent {
    public static final String EVENT_TYPE = "ORDER_CANCELLED";

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}