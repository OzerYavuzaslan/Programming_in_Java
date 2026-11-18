package com.omniflow.order.domain.port.out;

import lombok.Getter;

import java.util.Objects;

import static com.omniflow.order.domain.constant.OrderErrorMessages.*;

/**
 * Thrown when an order creation violates the unique idempotency constraint.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
@Getter
public class IdempotencyKeyConflictException extends RuntimeException {
    private final String tenantId;
    private final String idempotencyKey;

    public IdempotencyKeyConflictException(String tenantId, String idempotencyKey) {
        super(String.format(MSG_IDEMPOTENCY_CONFLICT_TEMPLATE, tenantId, idempotencyKey));
        this.tenantId = Objects.requireNonNull(tenantId, MSG_TENANT_ID_NULL);
        this.idempotencyKey = Objects.requireNonNull(idempotencyKey, MSG_IDEMPOTENCY_KEY_NULL);
    }
}
