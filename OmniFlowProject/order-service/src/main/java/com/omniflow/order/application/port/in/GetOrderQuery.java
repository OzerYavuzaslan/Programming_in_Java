package com.omniflow.order.application.port.in;

import com.omniflow.order.domain.model.OrderAggregate;

import java.util.UUID;

/**
 * Inbound query port defining single-order retrieval operations.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public interface GetOrderQuery {

    /**
     * Retrieves an order by its identifier within the tenant scope.
     *
     * @param tenantId multi-tenant isolation identifier
     * @param orderId  unique order identifier
     * @return the resolved {@link OrderAggregate}
     */
    OrderAggregate getOrder(String tenantId, UUID orderId);
}