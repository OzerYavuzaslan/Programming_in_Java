package com.omniflow.order.infra.persistence.repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Lightweight projection Data Transfer Object (DTO) populated directly from
 * the covering composite index via JPQL constructor expressions.
 *
 * @param id          the unique identifier of the order
 * @param totalAmount the total monetary amount
 * @param currency    the ISO-4217 currency code
 * @param createdAt   the creation timestamp of the order
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderSummaryProjectionDTO(
        UUID id,
        BigDecimal totalAmount,
        String currency,
        Instant createdAt
) {
}
