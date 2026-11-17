package com.omniflow.order.application.dto;

import com.omniflow.order.domain.model.OrderAggregate;

public record OrderCreationResultDTO(OrderAggregate orderAggregate, boolean replayed) {
    private static final String MSG_ORDER_NULL = "Order cannot be null";

    public OrderCreationResultDTO {
        if (orderAggregate == null) {
            throw new IllegalArgumentException(MSG_ORDER_NULL);
        }
    }
}