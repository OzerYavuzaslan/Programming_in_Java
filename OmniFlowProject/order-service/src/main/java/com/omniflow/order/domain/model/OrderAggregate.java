package com.omniflow.order.domain.model;

import com.omniflow.common.result.Result;
import org.apache.commons.collections4.CollectionUtils;

import java.time.Instant;
import java.util.*;

/**
 * Domain-Driven Design (DDD) Aggregate Root representing an Order.
 * <p>
 * Enforces business invariants:
 * <ul>
 *   <li>Identifiers (ID, Customer ID) and critical attributes cannot be null.</li>
 *   <li>Tenant ID must not be blank and cannot exceed 32 characters.</li>
 *   <li>Idempotency Key must not be blank and cannot exceed 64 characters.</li>
 *   <li>Order items list cannot be null or empty, with no null elements or duplicate products.</li>
 *   <li>Currency consistency across all items and order total is strictly validated.</li>
 *   <li>Total amount must equal the exact sum of line items (calculated via banker's rounding).</li>
 *   <li>Defensive copying is applied to immutable collections to safeguard against external mutation.</li>
 * </ul>
 *
 * @param id               the unique identifier of the order
 * @param tenantId         the multi-tenant partition identifier
 * @param customerId       the identifier of the ordering customer
 * @param idempotencyKey   the client-supplied idempotency token for duplicate suppression
 * @param orderItemList    the immutable list of order items
 * @param orderStatus      the current state machine status of the order
 * @param totalAmount      the total monetary amount of the order
 * @param statusChangeList the immutable timeline of order lifecycle status changes
 * @author Özer Yavuzaslan
 * @version 1.1.0
 */
public record OrderAggregate(
        UUID id,
        String tenantId,
        UUID customerId,
        String idempotencyKey,
        List<OrderItem> orderItemList,
        OrderStatus orderStatus,
        Money totalAmount,
        List<OrderStatusChange> statusChangeList
) {
    private static final String REASON_ORDER_CREATED = "ORDER_CREATED";
    private static final String REASON_PAYMENT_SETTLED = "PAYMENT_SETTLED";
    private static final String MSG_ITEM_NULL = "Order item cannot be null";
    private static final String MSG_ORDER_ID_NULL = "Order ID cannot be null";
    private static final String REASON_PAYMENT_INITIATED = "PAYMENT_INITIATED";
    private static final String MSG_STATUS_NULL = "Order status cannot be null";
    private static final String MSG_CUSTOMER_ID_NULL = "Customer ID cannot be null";
    private static final String MSG_TOTAL_AMOUNT_NULL = "Total amount cannot be null";
    private static final String MSG_DUPLICATE_PRODUCT = "Duplicate product in order: ";
    private static final String MSG_ITEMS_EMPTY = "Order items cannot be empty or null";
    private static final String MSG_CANNOT_CANCEL_ORDER = "Cannot cancel order in status: ";
    private static final String MSG_STATUS_CHANGES_NULL = "Status changes list cannot be null";
    private static final String MSG_TOTAL_MISMATCH = "Total amount does not match sum of items";
    private static final String MSG_CANNOT_SETTLE_PAYMENT = "Cannot settle payment for order in status: ";
    private static final String MSG_TENANT_ID_INVALID = "Tenant ID cannot be blank or exceed 32 characters";
    private static final String MSG_CANNOT_INITIATE_PAYMENT = "Cannot initiate payment for order in status: ";
    private static final String MSG_CURRENCY_MISMATCH = "Order item currency does not match order currency: ";
    private static final String MSG_IDEMPOTENCY_KEY_INVALID = "Idempotency key cannot be blank or exceed 64 characters";
    private static final String MSG_INVALID_CANCELLATION_REASON = "Cancellation reason cannot be blank or exceed 64 characters";

    public OrderAggregate {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(MSG_ORDER_ID_NULL);
        }

        if (Objects.isNull(tenantId) || tenantId.isBlank() || tenantId.length() > 32) {
            throw new IllegalArgumentException(MSG_TENANT_ID_INVALID);
        }

        if (Objects.isNull(customerId)) {
            throw new IllegalArgumentException(MSG_CUSTOMER_ID_NULL);
        }

        if (Objects.isNull(idempotencyKey) || idempotencyKey.isBlank() || idempotencyKey.length() > 64) {
            throw new IllegalArgumentException(MSG_IDEMPOTENCY_KEY_INVALID);
        }

        if (CollectionUtils.isEmpty(orderItemList)) {
            throw new IllegalArgumentException(MSG_ITEMS_EMPTY);
        }

        if (Objects.isNull(orderStatus)) {
            throw new IllegalArgumentException(MSG_STATUS_NULL);
        }

        if (Objects.isNull(totalAmount)) {
            throw new IllegalArgumentException(MSG_TOTAL_AMOUNT_NULL);
        }

        if (Objects.isNull(statusChangeList)) {
            throw new IllegalArgumentException(MSG_STATUS_CHANGES_NULL);
        }

        Set<String> productIdSet = new HashSet<>();
        Money calculatedTotal = Money.zero(totalAmount.currency());

        for (OrderItem item : orderItemList) {
            if (Objects.isNull(item)) {
                throw new IllegalArgumentException(MSG_ITEM_NULL);
            }

            if (!productIdSet.add(item.productId())) {
                throw new IllegalArgumentException(MSG_DUPLICATE_PRODUCT + item.productId());
            }

            if (!item.unitPrice().currency().equals(totalAmount.currency())) {
                throw new IllegalArgumentException(MSG_CURRENCY_MISMATCH + item.unitPrice().currency());
            }

            calculatedTotal = calculatedTotal.add(item.lineTotal());
        }

        if (totalAmount.amount().compareTo(calculatedTotal.amount()) != 0) {
            throw new IllegalArgumentException(MSG_TOTAL_MISMATCH);
        }

        orderItemList = List.copyOf(orderItemList);
        statusChangeList = List.copyOf(statusChangeList);
    }

    /**
     * Factory method to create a new OrderAggregate in {@link OrderStatus.Created} state.
     * Computes the total amount from items and initializes status change history.
     *
     * @param id             the unique identifier
     * @param tenantId       the tenant identifier
     * @param customerId     the customer identifier
     * @param idempotencyKey the idempotency token
     * @param orderItemList  the non-empty list of order items
     * @return a newly initialized OrderAggregate
     */
    public static OrderAggregate create(UUID id, String tenantId, UUID customerId, String idempotencyKey, List<OrderItem> orderItemList) {
        if (CollectionUtils.isEmpty(orderItemList)) {
            throw new IllegalArgumentException(MSG_ITEMS_EMPTY);
        }

        Money total = Money.zero(orderItemList.get(0).unitPrice().currency());
        for (OrderItem item : orderItemList) {
            if (Objects.isNull(item)) {
                throw new IllegalArgumentException(MSG_ITEM_NULL);
            }
            total = total.add(item.lineTotal());
        }

        OrderStatus initialStatus = new OrderStatus.Created();
        OrderStatusChange initialChange = new OrderStatusChange(
                UUID.randomUUID(),
                null,
                initialStatus,
                REASON_ORDER_CREATED,
                Instant.now()
        );

        return new OrderAggregate(
                id,
                tenantId,
                customerId,
                idempotencyKey,
                orderItemList,
                initialStatus,
                total,
                List.of(initialChange)
        );
    }

    /**
     * Factory method to reconstitute an existing OrderAggregate from the persistence layer.
     *
     * @param id             the order identifier
     * @param tenantId       the tenant identifier
     * @param customerId     the customer identifier
     * @param idempotencyKey the idempotency token
     * @param items          the items list
     * @param status         the persisted order status
     * @param totalAmount    the persisted total amount
     * @return the reconstituted OrderAggregate
     */
    public static OrderAggregate reconstitute(
            UUID id,
            String tenantId,
            UUID customerId,
            String idempotencyKey,
            List<OrderItem> items,
            OrderStatus status,
            Money totalAmount
    ) {
        return new OrderAggregate(
                id,
                tenantId,
                customerId,
                idempotencyKey,
                items,
                status,
                totalAmount,
                List.of()
        );
    }

    /**
     * Transitions order state from {@link OrderStatus.Created} to {@link OrderStatus.PaymentPending}.
     *
     * @return Result containing updated OrderAggregate or OrderError if transition is invalid
     */
    public Result<OrderAggregate, OrderError> markPaymentPending() {
        if (!(this.orderStatus instanceof OrderStatus.Created)) {
            return Result.failure(new OrderError.InvalidStateTransition(this.orderStatus, new OrderStatus.PaymentPending(),
                    MSG_CANNOT_INITIATE_PAYMENT + this.orderStatus.getClass().getSimpleName()));
        }

        OrderStatus newStatus = new OrderStatus.PaymentPending();
        OrderStatusChange change = new OrderStatusChange(
                UUID.randomUUID(),
                this.orderStatus,
                newStatus,
                REASON_PAYMENT_INITIATED,
                Instant.now()
        );

        List<OrderStatusChange> updatedHistory = new java.util.ArrayList<>(this.statusChangeList);
        updatedHistory.add(change);

        return Result.success(new OrderAggregate(
                this.id,
                this.tenantId,
                this.customerId,
                this.idempotencyKey,
                this.orderItemList,
                newStatus,
                this.totalAmount,
                updatedHistory
        ));
    }

    /**
     * Transitions order state from {@link OrderStatus.PaymentPending} to {@link OrderStatus.Paid}.
     *
     * @return Result containing updated OrderAggregate or OrderError if transition is invalid
     */
    public Result<OrderAggregate, OrderError> markPaid() {
        if (!(this.orderStatus instanceof OrderStatus.PaymentPending)) {
            return Result.failure(new OrderError.InvalidStateTransition(this.orderStatus, new OrderStatus.Paid(),
                    MSG_CANNOT_SETTLE_PAYMENT + this.orderStatus.getClass().getSimpleName()));
        }

        OrderStatus newStatus = new OrderStatus.Paid();
        OrderStatusChange orderStatusChange = new OrderStatusChange(
                UUID.randomUUID(),
                this.orderStatus,
                newStatus,
                REASON_PAYMENT_SETTLED,
                Instant.now()
        );

        List<OrderStatusChange> orderStatusChangeList = new java.util.ArrayList<>(this.statusChangeList);
        orderStatusChangeList.add(orderStatusChange);

        return Result.success(new OrderAggregate(
                this.id,
                this.tenantId,
                this.customerId,
                this.idempotencyKey,
                this.orderItemList,
                newStatus,
                this.totalAmount,
                orderStatusChangeList
        ));
    }

    /**
     * Cancels the order if it is in {@link OrderStatus.Created} or {@link OrderStatus.PaymentPending} state.
     *
     * @param cancelReason the business justification for cancellation
     * @return Result containing updated OrderAggregate or OrderError if cancellation is invalid
     */
    public Result<OrderAggregate, OrderError> cancel(String cancelReason) {
        if (Objects.isNull(cancelReason) || cancelReason.isBlank() || cancelReason.length() > 64) {
            return Result.failure(new OrderError.InvalidCancellationReason(MSG_INVALID_CANCELLATION_REASON));
        }

        boolean canCancel = (this.orderStatus instanceof OrderStatus.Created) || (this.orderStatus instanceof OrderStatus.PaymentPending);

        if (!canCancel) {
            return Result.failure(new OrderError.InvalidStateTransition(this.orderStatus, new OrderStatus.Cancelled(),
                    MSG_CANNOT_CANCEL_ORDER + this.orderStatus.getClass().getSimpleName()));
        }

        OrderStatus newOrderStatus = new OrderStatus.Cancelled();
        OrderStatusChange change = new OrderStatusChange(
                UUID.randomUUID(),
                this.orderStatus,
                newOrderStatus,
                cancelReason,
                Instant.now()
        );

        List<OrderStatusChange> updatedHistory = new java.util.ArrayList<>(this.statusChangeList);
        updatedHistory.add(change);

        return Result.success(new OrderAggregate(
                this.id,
                this.tenantId,
                this.customerId,
                this.idempotencyKey,
                this.orderItemList,
                newOrderStatus,
                this.totalAmount,
                updatedHistory
        ));
    }
}
