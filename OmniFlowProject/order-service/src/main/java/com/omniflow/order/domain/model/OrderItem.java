package com.omniflow.order.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Domain entity representing an individual item line within an {@link Order}.
 * <p>
 * Enforces domain invariants:
 * <ul>
 *   <li>Product ID cannot be null or blank.</li>
 *   <li>Quantity must be strictly greater than zero.</li>
 *   <li>Unit price cannot be null.</li>
 * </ul>
 *
 * @param productId the unique identifier of the product
 * @param quantity  the quantity ordered (must be &gt; 0)
 * @param unitPrice the unit price as an immutable {@link Money} value object
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderItem(UUID id, String productId, String productName, int quantity, Money unitPrice) {
    private static final String MSG_ID_NULL = "ID cannot be null";
    private static final String MSG_UNIT_PRICE_NULL = "Unit price cannot be null";
    private static final String MSG_INVALID_QUANTITY = "Quantity must be greater than zero";
    private static final String MSG_PRODUCT_ID = "Product ID cannot be blank or exceed 64 characters";
    private static final String MSG_PRODUCT_NAME = "Product name cannot be blank or exceed 255 characters";

    public OrderItem {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(MSG_ID_NULL);
        }

        if (Objects.isNull(productId) || productId.isBlank() || productId.length() > 64) {
            throw new IllegalArgumentException(MSG_PRODUCT_ID);
        }

        if (Objects.isNull(productName) || productName.isBlank() || productName.length() > 255) {
            throw new IllegalArgumentException(MSG_PRODUCT_NAME);
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(MSG_INVALID_QUANTITY);
        }

        if (Objects.isNull(unitPrice)) {
            throw new IllegalArgumentException(MSG_UNIT_PRICE_NULL);
        }
    }

    public Money lineTotal() {
        return unitPrice.multiplyAndRound(quantity);
    }
}