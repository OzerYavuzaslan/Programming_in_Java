package com.omniflow.order.domain.model;

import com.omniflow.common.result.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderAggregateTest {
    private static final int QUANTITY_2 = 2;
    private static final String CURRENCY_EUR = "EUR";
    private static final String CURRENCY_USD = "USD";
    private static final String PRODUCT_ID_1 = "PROD-MILK-01";
    private static final String PRODUCT_ID_2 = "PROD-BREAD-02";
    private static final String TENANT_ID = "tenant-amsterdam-01";
    private static final String IDEMPOTENCY_KEY = "idemp-key-abc-123";
    private static final String PRODUCT_NAME_2 = "Sourdough Bread 500g";
    private static final String PRODUCT_NAME_1 = "Organic Whole Milk 1L";
    private static final BigDecimal PRICE_2_50 = new BigDecimal("2.50");
    private static final BigDecimal PRICE_3_00 = new BigDecimal("3.00");
    private static final String IDEMPOTENCY_KEY_MORE_THAN_32_CHARACTERS = "C".repeat(33);

    @Test
    @DisplayName("Money - Fraction digits, rounding and scale overflow tests")
    void MoneyRoundingAndOverflowTests() {
        assertThrows(IllegalArgumentException.class, () -> new Money(new BigDecimal("2.12345"), CURRENCY_EUR));

        Money moneyEUR = new Money(new BigDecimal("2.1234"), CURRENCY_EUR);
        Money moneyUSD = new Money(new BigDecimal("3.4567"), CURRENCY_USD);
        assertThrows(IllegalArgumentException.class, () -> moneyEUR.add(moneyUSD));

        assertThrows(IllegalArgumentException.class, () -> moneyUSD.multiplyAndRound(0));
        assertThrows(IllegalArgumentException.class, () -> moneyEUR.multiplyAndRound(-1));
    }

    @Test
    @DisplayName("Should reconstitute order with provided status and empty status change history")
    void shouldReconstituteOrderWithProvidedStatusAndEmptyStatusHistory() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item1 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, QUANTITY_2, new Money(PRICE_2_50, CURRENCY_EUR));
        Money totalAmount = new Money(new BigDecimal("5.00"), CURRENCY_EUR);
        OrderStatus status = new OrderStatus.Created();

        Order order = new Order(orderId, TENANT_ID, customerId, IDEMPOTENCY_KEY, List.of(item1), status, totalAmount, List.of());

        assertEquals(orderId, order.id());
        assertEquals(TENANT_ID, order.tenantId());
        assertEquals(customerId, order.customerId());
        assertEquals(IDEMPOTENCY_KEY, order.idempotencyKey());
        assertEquals(1, order.orderItemList().size());
        assertEquals(0, order.statusChangeList().size());
        assertTrue(order.orderStatus() instanceof OrderStatus.Created);
        assertEquals(totalAmount.amount(), order.totalAmount().amount());
    }

    @Test
    @DisplayName("Should create order successfully with factory method and valid invariants")
    void shouldCreateOrderSuccessfully() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item1 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, QUANTITY_2, new Money(PRICE_2_50, CURRENCY_EUR));

        Order order = Order.create(orderId, TENANT_ID, customerId, IDEMPOTENCY_KEY, List.of(item1));

        assertEquals(orderId, order.id());
        assertEquals(TENANT_ID, order.tenantId());
        assertEquals(customerId, order.customerId());
        assertEquals(IDEMPOTENCY_KEY, order.idempotencyKey());
        assertEquals(1, order.orderItemList().size());
        assertEquals(1, order.statusChangeList().size());
        assertTrue(order.orderStatus() instanceof OrderStatus.Created);
        assertEquals(new BigDecimal("5.00"), order.totalAmount().amount());
        assertEquals("ORDER_CREATED", order.statusChangeList().get(0).reason());
    }

    @Test
    @DisplayName("Should fail when tenantId is blank")
    void shouldFailWhenTenantId() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item1 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));
        OrderItem item2 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_2, 1, new Money(PRICE_2_50, CURRENCY_EUR));

        assertThrows(IllegalArgumentException.class, () -> Order.create(
                orderId,
                "",
                customerId,
                IDEMPOTENCY_KEY,
                List.of(item1, item2)
        ));
    }

    @Test
    @DisplayName("Should fail when idempotencyKey is blank or more than 32 characters")
    void shouldFailWhenIdempotencyKeyIsBlank() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item1 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));
        OrderItem item2 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_2, 1, new Money(PRICE_2_50, CURRENCY_EUR));

        assertThrows(IllegalArgumentException.class, () -> Order.create(
                orderId,
                TENANT_ID,
                customerId,
                "",
                List.of(item1, item2)
        ));
    }

    @Test
    @DisplayName("Should fail when idempotencyKey is more than 32 characters")
    void shouldFailWhenIdempotencyKeyMoreThan32Characters() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item1 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));
        OrderItem item2 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_2, 1, new Money(PRICE_2_50, CURRENCY_EUR));

        assertThrows(IllegalArgumentException.class, () -> Order.create(
                orderId,
                TENANT_ID,
                customerId,
                IDEMPOTENCY_KEY_MORE_THAN_32_CHARACTERS,
                List.of(item1, item2)
        ));
    }

    @Test
    @DisplayName("Should fail when order item list has at least one null item")
    void shouldFailWhenOrderItemListHasNullItem() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item1 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));

        assertThrows(IllegalArgumentException.class, () -> Order.create(
                orderId,
                TENANT_ID,
                customerId,
                IDEMPOTENCY_KEY,
                Arrays.asList(item1, null)
        ));
    }

    @Test
    @DisplayName("Should fail when total amount does not match sum of item line totals")
    void shouldFailWhenTotalAmountMismatchesItemsSum() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, QUANTITY_2, new Money(PRICE_2_50, CURRENCY_EUR));
        Money wrongTotal = new Money(new BigDecimal("99.99"), CURRENCY_EUR);

        assertThrows(IllegalArgumentException.class, () -> new Order(
                orderId,
                TENANT_ID,
                customerId,
                IDEMPOTENCY_KEY,
                List.of(item),
                new OrderStatus.Created(),
                wrongTotal,
                List.of()
        ));
    }

    @Test
    @DisplayName("Should fail when invalid cancellation reason is provided")
    void shouldFailWhenInvalidCancellationReason() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));
        Order order = Order.create(orderId, TENANT_ID, customerId, IDEMPOTENCY_KEY, List.of(item));

        Result<Order, OrderError> blankResult = order.cancel("");
        assertTrue(blankResult instanceof Result.Failure<Order, OrderError>);

        Result<Order, OrderError> lengthExceededResult = order.cancel("A".repeat(65));
        assertTrue(lengthExceededResult instanceof Result.Failure<Order, OrderError>);
    }

    @Test
    @DisplayName("Should fail when order items contain duplicate products")
    void shouldFailWhenOrderHasDuplicateProduct() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item1 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));
        OrderItem item2 = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_2, 1, new Money(PRICE_2_50, CURRENCY_EUR));

        assertThrows(IllegalArgumentException.class, () -> Order.create(
                orderId,
                TENANT_ID,
                customerId,
                IDEMPOTENCY_KEY,
                List.of(item1, item2)
        ));
    }

    @Test
    @DisplayName("Should fail when item currency differs from order total currency")
    void shouldFailWhenCurrencyMismatches() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem itemEur = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));
        OrderItem itemUsd = new OrderItem(UUID.randomUUID(), PRODUCT_ID_2, PRODUCT_NAME_2, 1, new Money(PRICE_3_00, CURRENCY_USD));

        assertThrows(IllegalArgumentException.class, () -> Order.create(
                orderId,
                TENANT_ID,
                customerId,
                IDEMPOTENCY_KEY,
                List.of(itemEur, itemUsd)
        ));
    }

    @Test
    @DisplayName("Should protect immutability via defensive copy of items and status changes")
    void shouldProtectImmutabilityViaDefensiveCopy() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        List<OrderItem> mutableList = new ArrayList<>();
        mutableList.add(new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR)));

        Order order = Order.create(orderId, TENANT_ID, customerId, IDEMPOTENCY_KEY, mutableList);
        mutableList.clear();

        assertEquals(1, order.orderItemList().size());
        assertThrows(UnsupportedOperationException.class, () -> order.orderItemList().add(
                new OrderItem(UUID.randomUUID(), PRODUCT_ID_2, PRODUCT_NAME_2, 1, new Money(PRICE_3_00, CURRENCY_EUR))
        ));
    }

    @Test
    @DisplayName("Should successfully transition through valid lifecycle states: Created -> PaymentPending -> Paid")
    void shouldFollowValidStateLifecycle() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));
        Order order = Order.create(orderId, TENANT_ID, customerId, IDEMPOTENCY_KEY, List.of(item));

        Result<Order, OrderError> pendingResult = order.markPaymentPending();
        assertTrue(pendingResult.isSuccess());
        Order pendingOrder = pendingResult.getOrThrow(err -> new RuntimeException(err.toString()));
        assertTrue(pendingOrder.orderStatus() instanceof OrderStatus.PaymentPending);
        assertEquals(2, pendingOrder.statusChangeList().size());

        Result<Order, OrderError> paidResult = pendingOrder.markPaid();
        assertTrue(paidResult.isSuccess());
        Order paidOrder = paidResult.getOrThrow(err -> new RuntimeException(err.toString()));
        assertTrue(paidOrder.orderStatus() instanceof OrderStatus.Paid);
        assertEquals(3, paidOrder.statusChangeList().size());
    }

    @Test
    @DisplayName("Should reject invalid state transition: Created directly to Paid")
    void shouldRejectDirectTransitionToPaid() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));
        Order order = Order.create(orderId, TENANT_ID, customerId, IDEMPOTENCY_KEY, List.of(item));

        Result<Order, OrderError> paidResult = order.markPaid();
        assertTrue(paidResult.isFailure());
        OrderError error = ((Result.Failure<Order, OrderError>) paidResult).error();
        assertTrue(error instanceof OrderError.InvalidStateTransition);
    }

    @Test
    @DisplayName("Should successfully cancel order when Created or PaymentPending, but reject when Paid")
    void shouldHandleCancellationLifecycle() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item = new OrderItem(UUID.randomUUID(), PRODUCT_ID_1, PRODUCT_NAME_1, 1, new Money(PRICE_2_50, CURRENCY_EUR));
        Order order = Order.create(orderId, TENANT_ID, customerId, IDEMPOTENCY_KEY, List.of(item));

        Result<Order, OrderError> cancelResult = order.cancel("CUSTOMER_CHANGED_MIND");
        assertTrue(cancelResult.isSuccess());
        Order cancelledOrder = cancelResult.getOrThrow(err -> new RuntimeException(err.toString()));
        assertTrue(cancelledOrder.orderStatus() instanceof OrderStatus.Cancelled);

        Result<Order, OrderError> payCancelled = cancelledOrder.markPaymentPending();
        assertTrue(payCancelled.isFailure());
    }
}
