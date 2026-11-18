package com.omniflow.order.domain.port.out;

import com.omniflow.order.domain.model.Money;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static com.omniflow.order.domain.constant.OrderErrorMessages.*;

/**
 * Domain-level summary DTO for paginated order queries.
 *
 * @param id order unique identifier
 * @param totalAmount order monetary total amount
 * @param createdAt order creation timestamp in UTC
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderSummaryDTO(UUID id, Money totalAmount, Instant createdAt) {
    public OrderSummaryDTO {
        Objects.requireNonNull(id, MSG_ID_NULL);
        Objects.requireNonNull(totalAmount, MSG_TOTAL_AMOUNT_NULL);
        Objects.requireNonNull(createdAt, MSG_CREATED_AT_NULL);
    }
}
