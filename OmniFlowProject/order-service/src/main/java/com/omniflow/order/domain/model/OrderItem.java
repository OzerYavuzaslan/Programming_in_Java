package com.omniflow.order.domain.model;

import java.util.Objects;
import java.util.UUID;

import static com.omniflow.order.domain.constant.OrderErrorMessages.*;

/**
 * Domain entity representing an individual item line within an {@link OrderAggregate}.
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

    public OrderItem {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(MSG_ID_NULL);
        }

        if (Objects.isNull(productId) || productId.isBlank() || productId.length() > 64) {
            throw new IllegalArgumentException(MSG_PRODUCT_ID_INVALID);
        }

        if (Objects.isNull(productName) || productName.isBlank() || productName.length() > 255) {
            throw new IllegalArgumentException(MSG_PRODUCT_NAME_INVALID);
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
