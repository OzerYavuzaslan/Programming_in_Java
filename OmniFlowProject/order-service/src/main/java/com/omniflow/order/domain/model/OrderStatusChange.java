package com.omniflow.order.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static com.omniflow.order.domain.constant.OrderErrorMessages.*;

/**
 * Domain record tracking individual lifecycle state transition events of an {@link OrderAggregate}.
 * <p>
 * Forms the immutable audit timeline required for operational observability
 * and dispute resolution in distributed commerce environments.
 *
 * @param id         the unique identifier of the status change record
 * @param fromStatus the previous status before this transition (null for initial creation)
 * @param toStatus   the target status reached after this transition
 * @param reason     the optional business justification or system event trigger (max 64 characters)
 * @param changedAt  the timestamp marking when the transition took place
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderStatusChange(UUID id, OrderStatus fromStatus, OrderStatus toStatus, String reason, Instant changedAt) {

    public OrderStatusChange {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(MSG_ID_NULL);
        }

        if (Objects.isNull(toStatus)) {
            throw new IllegalArgumentException(MSG_TARGET_STATUS_NULL);
        }

        if (Objects.nonNull(reason) && reason.length() > 64) {
            throw new IllegalArgumentException(MSG_REASON_TOO_LONG);
        }

        if (Objects.isNull(changedAt)) {
            throw new IllegalArgumentException(MSG_CHANGED_AT_NULL);
        }
    }
}
