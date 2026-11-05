package com.omniflow.order.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Immutable Value Object representing a monetary amount with its corresponding ISO currency code.
 * <p>
 * Enforces domain invariants:
 * <ul>
 *   <li>Amount cannot be null or negative.</li>
 *   <li>Currency code cannot be null or blank, and is automatically normalized to upper-case.</li>
 * </ul>
 *
 * @param amount   the non-negative monetary value
 * @param currency the ISO-4217 currency code (e.g. EUR, USD)
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record Money(BigDecimal amount, String currency) {
    private static final String MSG_CURRENCY_BLANK = "Currency cannot be blank";
    private static final String MSG_AMOUNT_NEGATIVE = "Amount cannot be negative";

    public Money {
        if (Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(MSG_AMOUNT_NEGATIVE);
        }

        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException(MSG_CURRENCY_BLANK);
        }

        currency = currency.trim().toUpperCase();
    }
}