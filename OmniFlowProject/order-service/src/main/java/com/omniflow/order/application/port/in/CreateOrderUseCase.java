package com.omniflow.order.application.port.in;

import com.omniflow.order.application.command.CreateOrderCommand;
import com.omniflow.order.application.dto.OrderCreationResultDTO;

/**
 * Inbound driving port defining the contract for creating a new order.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public interface CreateOrderUseCase {
    /**
     * Executes the idempotent order creation workflow.
     *
     * @param createOrderCommand input createOrderCommand encapsulating customer, tenant, and basket createOrderItemCommandList
     * @return result wrapper containing the order aggregate and idempotency replay flag
     */
    OrderCreationResultDTO createOrder(CreateOrderCommand createOrderCommand);
}