package com.omniflow.order.application.command;

import java.util.Objects;
import java.util.UUID;

/**
 * Application command carrying the intent of cancelling an existing order.
 *
 * @param tenantId requesting tenant identifier
 * @param orderId  target order identifier
 * @param reason   business justification of the cancellation
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record CancelOrderCommand(String tenantId, UUID orderId, String reason) {
    private static final String MSG_ORDER_ID_NULL = "Order ID cannot be null";
    private static final String MSG_TENANT_ID_NULL = "Tenant ID cannot be null";

    public CancelOrderCommand {
        if (Objects.isNull(tenantId)) {
            throw new IllegalArgumentException(MSG_TENANT_ID_NULL);
        }

        if (Objects.isNull(orderId)) {
            throw new IllegalArgumentException(MSG_ORDER_ID_NULL);
        }
    }
}