package com.omniflow.order.infra.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Lightweight web response DTO for keyset-paginated order summaries.
 *
 * @param id          unique order identifier
 * @param totalAmount total monetary amount
 * @param currency    ISO-4217 currency code
 * @param createdAt   order creation timestamp in UTC (keyset cursor)
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderSummaryResponseDTO(
        UUID id,
        BigDecimal totalAmount,
        String currency,
        Instant createdAt
) {
}