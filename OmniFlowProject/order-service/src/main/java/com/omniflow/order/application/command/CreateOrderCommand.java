package com.omniflow.order.application.command;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Application command carrying the full intent of creating a new order.
 *
 * @param tenantId                   tenant (store) identifier
 * @param customerId                 ordering customer identifier
 * @param idempotencyKey             client-generated unique request key
 * @param createOrderItemCommandList basket lines of the order
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record CreateOrderCommand(String tenantId, UUID customerId, String idempotencyKey,
                                 List<CreateOrderItemCommand> createOrderItemCommandList) {
    private static final String MSG_TENANT_ID_NULL = "Tenant ID cannot be null";
    private static final String MSG_CUSTOMER_ID_NULL = "Customer ID cannot be null";
    private static final String MSG_IDEMPOTENCY_KEY_NULL = "Idempotency key cannot be null";
    private static final String MSG_ITEMS_EMPTY = "Order createOrderItemCommandList cannot be null or empty";

    public CreateOrderCommand {
        if (Objects.isNull(tenantId)) {
            throw new IllegalArgumentException(MSG_TENANT_ID_NULL);
        }

        if (Objects.isNull(customerId)) {
            throw new IllegalArgumentException(MSG_CUSTOMER_ID_NULL);
        }

        if (Objects.isNull(idempotencyKey)) {
            throw new IllegalArgumentException(MSG_IDEMPOTENCY_KEY_NULL);
        }

        if (CollectionUtils.isEmpty(createOrderItemCommandList)) {
            throw new IllegalArgumentException(MSG_ITEMS_EMPTY);
        }

        createOrderItemCommandList = List.copyOf(createOrderItemCommandList);
    }
}