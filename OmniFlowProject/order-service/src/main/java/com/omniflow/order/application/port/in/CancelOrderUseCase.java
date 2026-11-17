package com.omniflow.order.application.port.in;

import com.omniflow.common.result.Result;
import com.omniflow.order.application.command.CancelOrderCommand;
import com.omniflow.order.domain.model.OrderAggregate;
import com.omniflow.order.domain.model.OrderError;

/**
 * Inbound driving port defining the contract for order cancellation.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public interface CancelOrderUseCase {
    /**
     * Executes the order cancellation business workflow.
     *
     * @param command input command encapsulating order ID, tenant ID, and cancellation reason
     * @return functional {@link Result} holding the cancelled aggregate or domain error
     */
    Result<OrderAggregate, OrderError> cancelOrder(CancelOrderCommand command);
}