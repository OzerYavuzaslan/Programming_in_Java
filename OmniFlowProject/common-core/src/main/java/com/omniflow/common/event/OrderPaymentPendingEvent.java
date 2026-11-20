package com.omniflow.common.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Integration event emitted when an order transitions to payment pending state.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderPaymentPendingEvent(
        UUID eventId,
        Instant occurredAt,
        int schemaVersion,
        String tenantId,
        UUID orderId) implements OrderIntegrationEvent {
    public static final String EVENT_TYPE = "ORDER_PAYMENT_PENDING";

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}