package com.omniflow.order.infra.persistence.mapper;

import com.omniflow.order.domain.model.OrderStatus;

import java.util.Objects;

/**
 * Type-safe codec converting between sealed {@link OrderStatus} domain representations
 * and database-level status string codes.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public final class OrderStatusCodec {
    public static final String CODE_PAID = "PAID";
    public static final String CODE_CREATED = "CREATED";
    public static final String CODE_CANCELLED = "CANCELLED";
    public static final String CODE_PAYMENT_PENDING = "PAYMENT_PENDING";
    private static final String MSG_UNKNOWN_CODE = "Unknown order status code in database: ";

    private OrderStatusCodec() {
    }

    public static String toCode(OrderStatus orderStatus) {
        if (Objects.isNull(orderStatus)) {
            throw new IllegalArgumentException("OrderStatus cannot be null");
        }

        return switch (orderStatus) {
            case OrderStatus.Created c -> CODE_CREATED;
            case OrderStatus.PaymentPending pp -> CODE_PAYMENT_PENDING;
            case OrderStatus.Paid p -> CODE_PAID;
            case OrderStatus.Cancelled c -> CODE_CANCELLED;
        };
    }

    public static OrderStatus fromCode(String code) {
        if (Objects.isNull(code) || code.isBlank()) {
            throw new IllegalArgumentException("Status code cannot be null or blank");
        }

        return switch (code) {
            case CODE_CREATED -> new OrderStatus.Created();
            case CODE_PAYMENT_PENDING -> new OrderStatus.PaymentPending();
            case CODE_PAID -> new OrderStatus.Paid();
            case CODE_CANCELLED -> new OrderStatus.Cancelled();
            default -> throw new IllegalStateException(MSG_UNKNOWN_CODE + code);
        };
    }
}