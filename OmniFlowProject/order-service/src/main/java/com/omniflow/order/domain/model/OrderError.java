package com.omniflow.order.domain.model;

/**
 * Sealed interface representing domain-level errors that occur during order lifecycle operations.
 * <p>
 * Enforces Railway-Oriented Programming (ROP) principles by avoiding exception overhead
 * during anticipated state machine or business rule rejections.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public sealed interface OrderError permits OrderError.InvalidStateTransition, OrderError.InvalidCancellationReason {
    /**
     * Error emitted when a requested lifecycle status transition violates the state machine rules.
     *
     * @param currentStatus the existing state of the order
     * @param targetStatus  the state requested to transition into
     * @param message       explanatory diagnostic message
     */
    record InvalidStateTransition(OrderStatus currentStatus, OrderStatus targetStatus, String message) implements OrderError {}

    /**
     * Error emitted when a cancellation is requested with a blank or invalid reason parameter.
     *
     * @param message explanatory diagnostic message
     */
    record InvalidCancellationReason(String message) implements OrderError {}
}
