package com.omniflow.order.application.exception;

import lombok.Getter;

import java.util.UUID;

import static com.omniflow.order.domain.constant.OrderErrorMessages.MSG_ORDER_NOT_FOUND_TEMPLATE;

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

    public OrderNotFoundException(String tenantId, UUID orderId) {
        super(String.format(MSG_ORDER_NOT_FOUND_TEMPLATE, orderId, tenantId));
        this.orderId = orderId;
        this.tenantId = tenantId;
    }
}
