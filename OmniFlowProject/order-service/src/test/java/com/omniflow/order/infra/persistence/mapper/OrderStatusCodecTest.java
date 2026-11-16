package com.omniflow.order.infra.persistence.mapper;

import com.omniflow.order.domain.model.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderStatusCodec Unit Tests")
class OrderStatusCodecTest {
    @Test
    @DisplayName("Should correctly map all domain OrderStatus instances to database codes")
    void shouldMapAllDomainStatusesToCodes() {
        assertEquals(OrderStatusCodec.CODE_CREATED, OrderStatusCodec.toCode(new OrderStatus.Created()));
        assertEquals(OrderStatusCodec.CODE_PAYMENT_PENDING, OrderStatusCodec.toCode(new OrderStatus.PaymentPending()));
        assertEquals(OrderStatusCodec.CODE_PAID, OrderStatusCodec.toCode(new OrderStatus.Paid()));
        assertEquals(OrderStatusCodec.CODE_CANCELLED, OrderStatusCodec.toCode(new OrderStatus.Cancelled()));
    }

    @Test
    @DisplayName("Should correctly map all database codes back to domain OrderStatus instances")
    void shouldMapAllCodesToDomainStatuses() {
        assertInstanceOf(OrderStatus.Created.class, OrderStatusCodec.fromCode(OrderStatusCodec.CODE_CREATED));
        assertInstanceOf(OrderStatus.PaymentPending.class, OrderStatusCodec.fromCode(OrderStatusCodec.CODE_PAYMENT_PENDING));
        assertInstanceOf(OrderStatus.Paid.class, OrderStatusCodec.fromCode(OrderStatusCodec.CODE_PAID));
        assertInstanceOf(OrderStatus.Cancelled.class, OrderStatusCodec.fromCode(OrderStatusCodec.CODE_CANCELLED));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when converting null OrderStatus to code")
    void shouldThrowExceptionWhenOrderStatusIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> OrderStatusCodec.toCode(null)
        );

        assertEquals("OrderStatus cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("Should throw IllegalArgumentException when converting null or blank code to OrderStatus")
    void shouldThrowExceptionWhenCodeIsNullOrEmpty(String invalidCode) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> OrderStatusCodec.fromCode(invalidCode)
        );

        assertEquals("Status code cannot be null or blank", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalStateException when encountering an unknown database status code")
    void shouldThrowExceptionWhenCodeIsUnknown() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> OrderStatusCodec.fromCode("UNKNOWN_STATUS")
        );

        assertTrue(exception.getMessage().contains("Unknown order status code in database: UNKNOWN_STATUS"));
    }
}