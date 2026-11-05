package com.omniflow.order.domain.model;

import java.util.List;

/**
 * Domain-Driven Design (DDD) Aggregate Root representing an Order.
 * <p>
 * Enforces core business invariants:
 * <ul>
 *   <li>Order ID and Customer ID cannot be null or blank.</li>
 *   <li>Order items list cannot be null or empty.</li>
 *   <li>Status and Total Amount cannot be null.</li>
 *   <li>Defensive copying is applied to {@code orderItemList} to safeguard immutability against external mutations.</li>
 * </ul>
 *
 * @param id             the unique identifier of the order
 * @param customerId     the identifier of the ordering customer
 * @param orderItemList  the immutable list of order items
 * @param orderStatus    the current state machine status of the order
 * @param totalAmount    the total monetary amount of the order
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record Order(String id, String customerId, List<OrderItem> orderItemList, OrderStatus orderStatus, Money totalAmount) {
    private static final String MSG_ORDER_ID_BLANK = "Order ID cannot be blank";
    private static final String MSG_ITEMS_EMPTY = "Order items cannot be empty";
    private static final String MSG_STATUS_NULL = "Order status cannot be null";
    private static final String MSG_TOTAL_AMOUNT_NULL = "Total amount cannot be null";
    private static final String MSG_CUSTOMER_ID_BLANK = "Customer ID cannot be blank";

    public Order {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(MSG_ORDER_ID_BLANK);
        }

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException(MSG_CUSTOMER_ID_BLANK);
        }

        if (orderItemList == null || orderItemList.isEmpty()) {
            throw new IllegalArgumentException(MSG_ITEMS_EMPTY);
        }

        if (orderStatus == null) {
            throw new IllegalArgumentException(MSG_STATUS_NULL);
        }

        if (totalAmount == null) {
            throw new IllegalArgumentException(MSG_TOTAL_AMOUNT_NULL);
        }

        orderItemList = List.copyOf(orderItemList);
    }
}