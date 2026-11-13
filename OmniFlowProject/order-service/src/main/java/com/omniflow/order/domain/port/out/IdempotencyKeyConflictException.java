package com.omniflow.order.domain.port.out;

import lombok.Getter;

import java.util.Objects;

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
        super(String.format("Idempotency conflict for tenant '%s' and key '%s'", tenantId, idempotencyKey));
        this.tenantId = Objects.requireNonNull(tenantId, "tenantId cannot be null");
        this.idempotencyKey = Objects.requireNonNull(idempotencyKey, "idempotencyKey cannot be null");
    }
}