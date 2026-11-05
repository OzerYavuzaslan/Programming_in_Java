package com.omniflow.order.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderAggregateTest {
    private static final Integer QUANTITY = 2;
    private static final String CURRENCY_EUR = "EUR";
    private static final String ORDER_ID = "ORD-001";
    private static final String PRODUCT_ID = "PROD-999";
    private static final String CUSTOMER_ID = "CUST-100";
    private static final BigDecimal VALID_PRICE = new BigDecimal("25.50");
    private static final BigDecimal NEGATIVE_PRICE = new BigDecimal("-10.00");

    @Test
    void shouldCreateOrderSuccessfully() {
        Money money = new Money(VALID_PRICE, CURRENCY_EUR);
        OrderItem orderItem = new OrderItem(PRODUCT_ID, QUANTITY, money);
        Order order = new Order(ORDER_ID, CUSTOMER_ID, List.of(orderItem), new OrderStatus.Created(), money);

        assertEquals(ORDER_ID, order.id());
        assertEquals(1, order.orderItemList().size());
        assertTrue(order.orderStatus() instanceof OrderStatus.Created);
    }

    @Test
    void shouldFailWhenTotalAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Money(NEGATIVE_PRICE, CURRENCY_EUR));
    }

    @Test
    void shouldFailWithEmptyOrderItemList() {
        assertThrows(IllegalArgumentException.class, () -> new Order(ORDER_ID, CUSTOMER_ID, List.of(), new OrderStatus.Created(), new Money(VALID_PRICE, CURRENCY_EUR)));
    }

    @Test
    void shouldProtectImmutabilityViaDefensiveCopy() {
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem(PRODUCT_ID, QUANTITY, new Money(VALID_PRICE, CURRENCY_EUR)));
        Order order = new Order(ORDER_ID, CUSTOMER_ID, orderItemList, new OrderStatus.Created(), new Money(VALID_PRICE, CURRENCY_EUR));
        orderItemList.clear();
        assertEquals(1, order.orderItemList().size());
        assertThrows(UnsupportedOperationException.class, () -> order.orderItemList().add(new OrderItem(PRODUCT_ID, QUANTITY, new Money(VALID_PRICE, CURRENCY_EUR))));
    }
}