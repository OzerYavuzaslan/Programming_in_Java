package com.omniflow.order.infra.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Web response DTO representing a single line item in an order response.
 *
 * @param id          unique item identifier
 * @param productId   catalogue product identifier
 * @param productName product name snapshot
 * @param quantity    ordered quantity
 * @param unitPrice   unit price amount
 * @param lineTotal   computed line total amount
 * @param currency    ISO-4217 currency code
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderItemResponseDTO(
        UUID id,
        String productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal,
        String currency
) {
}