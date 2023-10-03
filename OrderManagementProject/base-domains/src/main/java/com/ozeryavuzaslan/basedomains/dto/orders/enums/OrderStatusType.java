package com.ozeryavuzaslan.basedomains.dto.orders.enums;

public enum OrderStatusType {
    TAKEN,
    APPROVED,
    PREPARING,
    IN_CARGO,
    CANCELED_BY_CUSTOMER,
    CANCELED_NOT_ENOUGH_STOCK,
    CANCELED_PAYMENT_FAILED,
    CANCELED_SERVICE_DOWN
}