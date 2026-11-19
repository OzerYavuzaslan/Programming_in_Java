package com.omniflow.order.infra.web.dto;

import com.omniflow.common.validation.ValidCurrency;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Web request DTO representing a single item line in an order creation request.
 *
 * @param productId   catalogue product identifier
 * @param productName product name snapshot at purchase time
 * @param quantity    ordered quantity (strictly positive)
 * @param unitPrice   unit price amount (non-negative, max 4 decimal places)
 * @param currency    ISO-4217 currency code
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderItemRequestDTO(
        @NotBlank @Size(max = 64) String productId,
        @NotBlank @Size(max = 255) String productName,
        @Positive int quantity,
        @NotNull @PositiveOrZero @Digits(integer = 15, fraction = 4) BigDecimal unitPrice,
        @NotBlank @ValidCurrency String currency
) {
}