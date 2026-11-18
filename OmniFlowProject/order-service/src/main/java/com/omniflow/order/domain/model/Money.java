package com.omniflow.order.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

import static com.omniflow.order.domain.constant.OrderErrorMessages.*;

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
    private static final int MAX_SCALE = 4;

    public Money {
        if (Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(MSG_AMOUNT_NEGATIVE);
        }

        if (amount.scale() > MAX_SCALE) {
            throw new IllegalArgumentException(MSG_SCALE_EXCEEDED_PREFIX + MAX_SCALE);
        }

        if (Objects.isNull(currency) || currency.isBlank()) {
            throw new IllegalArgumentException(MSG_CURRENCY_BLANK);
        }

        currency = currency.trim().toUpperCase(Locale.ROOT);

        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(MSG_INVALID_CURRENCY_CODE + currency, e);
        }
    }

    public Money add(Money other) {
        if (Objects.isNull(other) || !this.currency.equals(other.currency())) {
            throw new IllegalArgumentException(MSG_CURRENCY_MISMATCH_ADDITION);
        }

        return new Money(this.amount.add(other.amount()), this.currency);
    }

    public Money multiplyAndRound(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(MSG_INVALID_QUANTITY);
        }

        int fractionDigits = Currency.getInstance(this.currency).getDefaultFractionDigits();
        int scale = Math.max(fractionDigits, 0);
        BigDecimal multiplied = this.amount.multiply(BigDecimal.valueOf(quantity)).setScale(scale, RoundingMode.HALF_EVEN);
        return new Money(multiplied, this.currency);
    }

    public static Money zero(String currency) {
        if (Objects.isNull(currency) || currency.isBlank()) {
            throw new IllegalArgumentException(MSG_CURRENCY_BLANK);
        }

        int fractionDigits = Currency.getInstance(currency.trim().toUpperCase(Locale.ROOT)).getDefaultFractionDigits();
        int scale = Math.max(fractionDigits, 0);
        BigDecimal zeroAmount = BigDecimal.ZERO.setScale(scale, RoundingMode.UNNECESSARY);
        return new Money(zeroAmount, currency);
    }
}
