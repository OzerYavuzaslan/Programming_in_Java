package com.omniflow.order.domain.model;

/**
 * Represents the lifecycle status of an {@link Order} within the system.
 * <p>
 * Implemented as a Java 21 sealed interface to restrict state variations
 * to an exhaustive, strictly controlled state machine.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public sealed interface OrderStatus permits OrderStatus.Created, OrderStatus.PaymentPending, OrderStatus.Paid, OrderStatus.Cancelled {
    /**
     * Initial state when an order is created by the customer.
     */
    record Created() implements OrderStatus {}

    /**
     * State when payment processing has been initiated but not yet completed.
     */
    record PaymentPending() implements OrderStatus {}

    /**
     * State when payment has been successfully settled.
     */
    record Paid() implements OrderStatus {}

    /**
     * Terminal state indicating the order was cancelled.
     */
    record Cancelled() implements OrderStatus {}
}