package com.omniflow.order.infra.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Web request DTO carrying the payload for creating a new order.
 *
 * @param customerId              ordering customer identifier
 * @param orderItemRequestDTOList non-empty list of basket items
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record CreateOrderRequestDTO(@NotNull UUID customerId,
                                    @NotEmpty @Valid List<OrderItemRequestDTO> orderItemRequestDTOList
) {
}