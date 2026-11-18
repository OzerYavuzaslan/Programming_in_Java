package com.omniflow.order.domain.constant;

/**
 * Immutable utility class holding centralized error and validation messages
 * shared across domain, application, and infrastructure layers of order-service.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public final class OrderErrorMessages {
    private OrderErrorMessages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // --- Common Identifier & Nullability Messages ---
    public static final String MSG_ID_NULL = "ID cannot be null";
    public static final String MSG_ORDER_ID_NULL = "Order ID cannot be null";
    public static final String MSG_TENANT_ID_NULL = "Tenant ID cannot be null";
    public static final String MSG_TENANT_ID_BLANK = "Tenant ID cannot be null or blank";
    public static final String MSG_TENANT_ID_INVALID = "Tenant ID cannot be blank or exceed 32 characters";
    public static final String MSG_CUSTOMER_ID_NULL = "Customer ID cannot be null";
    public static final String MSG_IDEMPOTENCY_KEY_NULL = "Idempotency key cannot be null";
    public static final String MSG_IDEMPOTENCY_KEY_INVALID = "Idempotency key cannot be blank or exceed 64 characters";

    // --- Order Aggregate, Status & Command Messages ---
    public static final String MSG_ORDER_NULL = "OrderAggregate cannot be null";
    public static final String MSG_STATUS_NULL = "Order status cannot be null";
    public static final String MSG_ORDER_STATUS_NULL = "OrderStatus cannot be null";
    public static final String MSG_STATUS_CODE_BLANK = "Status code cannot be null or blank";
    public static final String MSG_UNKNOWN_STATUS_CODE = "Unknown order status code in database: ";
    public static final String MSG_TOTAL_AMOUNT_NULL = "Total amount cannot be null";
    public static final String MSG_CREATED_AT_NULL = "Created at timestamp cannot be null";
    public static final String MSG_CHANGED_AT_NULL = "Changed at timestamp cannot be null";
    public static final String MSG_TARGET_STATUS_NULL = "Target status cannot be null";
    public static final String MSG_STATUS_CHANGES_NULL = "Status changes list cannot be null";
    public static final String MSG_REASON_TOO_LONG = "Reason cannot exceed 64 characters";
    public static final String MSG_INVALID_CANCELLATION_REASON = "Cancellation reason cannot be blank or exceed 64 characters";
    public static final String MSG_TOTAL_MISMATCH = "Total amount does not match sum of items";
    public static final String MSG_CREATE_COMMAND_NULL = "CreateOrderCommand cannot be null";
    public static final String MSG_CANCEL_COMMAND_NULL = "CancelOrderCommand cannot be null";
    public static final String MSG_LIMIT_OUT_OF_RANGE = "Limit must be between 1 and 100, received: ";

    // --- Order Item & Product Messages ---
    public static final String MSG_ITEMS_EMPTY = "Order items cannot be empty or null";
    public static final String MSG_ITEM_NULL = "Order item cannot be null";
    public static final String MSG_PRODUCT_ID_NULL = "Product ID cannot be null";
    public static final String MSG_PRODUCT_ID_INVALID = "Product ID cannot be blank or exceed 64 characters";
    public static final String MSG_PRODUCT_NAME_NULL = "Product name cannot be null";
    public static final String MSG_PRODUCT_NAME_INVALID = "Product name cannot be blank or exceed 255 characters";
    public static final String MSG_DUPLICATE_PRODUCT = "Duplicate product in order: ";
    public static final String MSG_INVALID_QUANTITY = "Quantity must be greater than zero";

    // --- Money & Currency Messages ---
    public static final String MSG_UNIT_PRICE_NULL = "Unit price cannot be null";
    public static final String MSG_CURRENCY_NULL = "Currency cannot be null";
    public static final String MSG_CURRENCY_BLANK = "Currency cannot be blank";
    public static final String MSG_INVALID_CURRENCY_CODE = "Invalid ISO-4217 currency code: ";
    public static final String MSG_AMOUNT_NEGATIVE = "Amount cannot be negative";
    public static final String MSG_SCALE_EXCEEDED_PREFIX = "Amount scale cannot exceed ";
    public static final String MSG_CURRENCY_MISMATCH_ADDITION = "Currencies must match for addition";
    public static final String MSG_ITEM_CURRENCY_MISMATCH = "Order item currency does not match order currency: ";

    // --- State Transition & Exception Templates ---
    public static final String MSG_CANNOT_INITIATE_PAYMENT = "Cannot initiate payment for order in status: ";
    public static final String MSG_CANNOT_SETTLE_PAYMENT = "Cannot settle payment for order in status: ";
    public static final String MSG_CANNOT_CANCEL_ORDER = "Cannot cancel order in status: ";
    public static final String MSG_ORDER_NOT_FOUND_TEMPLATE = "Order '%s' not found for tenant '%s'";
    public static final String MSG_IDEMPOTENCY_CONFLICT_TEMPLATE = "Idempotency conflict for tenant '%s' and key '%s'";
    public static final String MSG_IDEMPOTENCY_REPLAY_LOAD_FAILED = "Idempotency conflict detected for key '%s', but existing order could not be loaded.";
}