package com.omniflow.order.application.dto;

import com.omniflow.order.domain.model.OrderAggregate;

import java.util.Objects;

import static com.omniflow.order.domain.constant.OrderErrorMessages.MSG_ORDER_NULL;

public record OrderCreationResultDTO(OrderAggregate orderAggregate, boolean replayed) {

    public OrderCreationResultDTO {
        if (Objects.isNull(orderAggregate)) {
            throw new IllegalArgumentException(MSG_ORDER_NULL);
        }
    }
}
