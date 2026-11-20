package com.omniflow.common.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Integration event emitted when an order payment is settled.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderPaidEvent(
        UUID eventId,
        Instant occurredAt,
        int schemaVersion,
        String tenantId,
        UUID orderId) implements OrderIntegrationEvent {
    public static final String EVENT_TYPE = "ORDER_PAID";

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}