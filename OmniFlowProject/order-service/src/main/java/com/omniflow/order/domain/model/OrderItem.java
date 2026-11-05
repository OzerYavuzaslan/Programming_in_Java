package com.omniflow.order.domain.model;

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
public record OrderItem(String productId, int quantity, Money unitPrice) {
    private static final String MSG_UNIT_PRICE_NULL = "Unit price cannot be null";
    private static final String MSG_PRODUCT_ID_BLANK = "Product ID cannot be blank";
    private static final String MSG_INVALID_QUANTITY = "Quantity must be greater than zero";

    public OrderItem {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException(MSG_PRODUCT_ID_BLANK);
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(MSG_INVALID_QUANTITY);
        }

        if (unitPrice == null) {
            throw new IllegalArgumentException(MSG_UNIT_PRICE_NULL);
        }
    }
}