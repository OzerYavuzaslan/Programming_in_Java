package com.omniflow.order.application.command;

import java.util.Objects;
import java.util.UUID;

import static com.omniflow.order.domain.constant.OrderErrorMessages.MSG_ORDER_ID_NULL;
import static com.omniflow.order.domain.constant.OrderErrorMessages.MSG_TENANT_ID_NULL;

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

    public CancelOrderCommand {
        if (Objects.isNull(tenantId)) {
            throw new IllegalArgumentException(MSG_TENANT_ID_NULL);
        }

        if (Objects.isNull(orderId)) {
            throw new IllegalArgumentException(MSG_ORDER_ID_NULL);
        }
    }
}
