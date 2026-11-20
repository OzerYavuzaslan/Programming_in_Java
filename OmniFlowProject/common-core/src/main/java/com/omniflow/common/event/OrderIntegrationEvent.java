package com.omniflow.common.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Sealed contract for all order lifecycle integration events published to Kafka.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public sealed interface OrderIntegrationEvent permits OrderCreatedEvent, OrderPaymentPendingEvent, OrderPaidEvent, OrderCancelledEvent {
    UUID eventId();
    Instant occurredAt();
    int schemaVersion();
    String tenantId();
    UUID orderId();
    String eventType();
}