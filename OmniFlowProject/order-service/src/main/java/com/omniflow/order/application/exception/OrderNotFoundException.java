package com.omniflow.order.application.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Thrown when an order does not exist or does not belong to the requesting tenant.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
@Getter
public class OrderNotFoundException extends RuntimeException {
    private final UUID orderId;
    private final String tenantId;
    private static final String MSG_TEMPLATE = "Order '%s' not found for tenant '%s'";

    public OrderNotFoundException(String tenantId, UUID orderId) {
        super(String.format(MSG_TEMPLATE, orderId, tenantId));
        this.orderId = orderId;
        this.tenantId = tenantId;
    }
}