package com.omniflow.order.application.port.in;

import com.omniflow.order.domain.model.OrderStatus;
import com.omniflow.order.domain.port.out.OrderSummaryDTO;

import java.time.Instant;
import java.util.List;

/**
 * Inbound query port defining multi-order listing operations.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public interface ListOrdersQuery {

    /**
     * Retrieves paginated/summarized orders matching criteria.
     *
     * @param tenantId      multi-tenant isolation identifier
     * @param status        target order status filter (optional)
     * @param createdBefore upper bound creation timestamp for keyset paging (optional)
     * @param limit         maximum number of records to return (1-100)
     * @return immutable list of {@link OrderSummaryDTO}
     */
    List<OrderSummaryDTO> listOrders(String tenantId, OrderStatus status, Instant createdBefore, int limit);
}