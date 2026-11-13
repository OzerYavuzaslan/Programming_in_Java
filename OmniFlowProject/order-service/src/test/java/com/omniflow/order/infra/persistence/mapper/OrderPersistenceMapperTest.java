package com.omniflow.order.infra.persistence.mapper;

import com.omniflow.order.domain.model.*;
import com.omniflow.order.infra.persistence.entity.OrderItemJpaEntity;
import com.omniflow.order.infra.persistence.entity.OrderJpaEntity;
import com.omniflow.order.infra.persistence.entity.OrderStatusHistoryJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderPersistenceMapper Unit Tests")
class OrderPersistenceMapperTest {

    private OrderPersistenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OrderPersistenceMapper();
    }

    @Test
    @DisplayName("Should map Domain OrderAggregate to new OrderJpaEntity with bidirectional item references")
    void shouldMapDomainOrderToNewEntity() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderItem item1 = new OrderItem(UUID.randomUUID(), "PROD-1", "Laptop", 1, new Money(new BigDecimal("1000.0000"), "EUR"));
        OrderItem item2 = new OrderItem(UUID.randomUUID(), "PROD-2", "Mouse", 2, new Money(new BigDecimal("25.0000"), "EUR"));
        OrderAggregate domainOrder = OrderAggregate.create(orderId, "tenant-amsterdam", customerId, "idemp-key-1", List.of(item1, item2));

        OrderJpaEntity entity = mapper.toNewEntity(domainOrder);

        assertNotNull(entity);
        assertEquals(orderId, entity.getId());
        assertEquals("tenant-amsterdam", entity.getTenantId());
        assertEquals(customerId, entity.getCustomerId());
        assertEquals("idemp-key-1", entity.getIdempotencyKey());
        assertEquals("CREATED", entity.getStatus());
        assertEquals(new BigDecimal("1050.00"), entity.getTotalAmount());
        assertEquals("EUR", entity.getCurrency());
        assertNull(entity.getVersion(), "New entity version must be null for Spring Data isNew check");

        assertEquals(2, entity.getOrderItemList().size());
        OrderItemJpaEntity entityItem1 = entity.getOrderItemList().get(0);
        assertEquals(item1.id(), entityItem1.getId());
        assertEquals("PROD-1", entityItem1.getProductId());
        assertEquals(1, entityItem1.getQuantity());
        assertSame(entity, entityItem1.getOrder(), "OrderItemJpaEntity.order must reference parent OrderJpaEntity");
    }

    @Test
    @DisplayName("Should map OrderJpaEntity back to Domain OrderAggregate maintaining complete parity")
    void shouldMapEntityToDomainOrder() {
        OrderJpaEntity entity = OrderJpaEntity.getNewOrderJpaEntity();
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        entity.setId(orderId);
        entity.setTenantId("tenant-rotterdam");
        entity.setCustomerId(customerId);
        entity.setIdempotencyKey("idemp-key-2");
        entity.setStatus("PAYMENT_PENDING");
        entity.setTotalAmount(new BigDecimal("50.0000"));
        entity.setCurrency("EUR");

        OrderItemJpaEntity itemEntity = OrderItemJpaEntity.getOrderItemJpaEntity();
        itemEntity.setId(UUID.randomUUID());
        itemEntity.setProductId("PROD-KEYBOARD");
        itemEntity.setProductName("Mechanical Keyboard");
        itemEntity.setQuantity(1);
        itemEntity.setUnitPrice(new BigDecimal("50.0000"));
        itemEntity.setCurrency("EUR");
        entity.addOrderItem(itemEntity);

        OrderAggregate domainOrder = mapper.toDomain(entity);

        assertNotNull(domainOrder);
        assertEquals(orderId, domainOrder.id());
        assertEquals("tenant-rotterdam", domainOrder.tenantId());
        assertEquals(customerId, domainOrder.customerId());
        assertEquals("idemp-key-2", domainOrder.idempotencyKey());
        assertInstanceOf(OrderStatus.PaymentPending.class, domainOrder.orderStatus());
        assertEquals(new BigDecimal("50.0000"), domainOrder.totalAmount().amount());
        assertEquals("EUR", domainOrder.totalAmount().currency());
        assertEquals(1, domainOrder.orderItemList().size());
        assertEquals("PROD-KEYBOARD", domainOrder.orderItemList().get(0).productId());
    }

    @Test
    @DisplayName("Should apply domain changes to existing entity by only updating mutable status")
    void shouldApplyChangesToExistingEntity() {
        OrderJpaEntity entity = OrderJpaEntity.getNewOrderJpaEntity();
        entity.setStatus("CREATED");

        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                "PROD-TEST",
                "Test Product",
                1,
                new Money(new BigDecimal("100.00"), "EUR")
        );

        OrderAggregate domainOrder = OrderAggregate.reconstitute(
                UUID.randomUUID(),
                "tenant-1",
                UUID.randomUUID(),
                "idemp-1",
                List.of(item),
                new OrderStatus.Paid(),
                new Money(new BigDecimal("100.00"), "EUR")
        );

        mapper.applyChanges(domainOrder, entity);

        assertEquals("PAID", entity.getStatus(), "applyChanges must update status to PAID");
    }

    @Test
    @DisplayName("Should map domain OrderStatusChange events to OrderStatusHistoryJpaEntity list")
    void shouldMapHistoryEntities() {
        UUID orderId = UUID.randomUUID();
        UUID changeId = UUID.randomUUID();
        Instant now = Instant.now();
        OrderStatusChange change = new OrderStatusChange(
                changeId,
                new OrderStatus.Created(),
                new OrderStatus.PaymentPending(),
                "PAYMENT_TRIGGERED",
                now
        );

        // Act
        List<OrderStatusHistoryJpaEntity> historyList = mapper.toHistoryEntities(orderId, List.of(change));

        assertNotNull(historyList);
        assertEquals(1, historyList.size());
        OrderStatusHistoryJpaEntity historyEntity = historyList.get(0);
        assertEquals(changeId, historyEntity.getId());
        assertEquals(orderId, historyEntity.getOrderId());
        assertEquals("CREATED", historyEntity.getFromStatus());
        assertEquals("PAYMENT_PENDING", historyEntity.getToStatus());
        assertEquals("PAYMENT_TRIGGERED", historyEntity.getReason());
        assertEquals(now, historyEntity.getChangedAt());
        assertTrue(historyEntity.isNew(), "Persistable.isNew must be true for append-only insert optimization");
    }
}