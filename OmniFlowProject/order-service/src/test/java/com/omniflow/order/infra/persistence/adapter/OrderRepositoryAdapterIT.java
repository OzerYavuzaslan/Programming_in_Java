package com.omniflow.order.infra.persistence.adapter;

import com.omniflow.common.persistence.autoconfigure.OmniFlowJpaAuditingAutoConfiguration;
import com.omniflow.order.domain.model.Money;
import com.omniflow.order.domain.model.OrderAggregate;
import com.omniflow.order.domain.model.OrderItem;
import com.omniflow.order.domain.model.OrderStatus;
import com.omniflow.order.domain.port.out.IdempotencyKeyConflictException;
import com.omniflow.order.domain.port.out.OrderSummaryDTO;
import com.omniflow.order.infra.persistence.entity.OrderStatusHistoryJpaEntity;
import com.omniflow.order.infra.persistence.mapper.OrderPersistenceMapper;
import com.omniflow.order.infra.persistence.repository.SpringDataOrderJpaRepository;
import com.omniflow.order.infra.persistence.repository.SpringDataOrderStatusHistoryJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({OrderRepositoryAdapter.class, OrderPersistenceMapper.class, OmniFlowJpaAuditingAutoConfiguration.class})
@DisplayName("OrderRepositoryAdapter Integration Tests (PostgreSQL 16 Testcontainers)")
class OrderRepositoryAdapterIT {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private OrderRepositoryAdapter orderRepositoryAdapter;

    @Autowired
    private SpringDataOrderStatusHistoryJpaRepository springDataOrderStatusHistoryJpaRepository;

    @Autowired
    private SpringDataOrderJpaRepository orderJpaRepository;

    @Test
    @DisplayName("Should persist new order and status history atomically into real PostgreSQL")
    void shouldPersistNewOrderAndHistoryAtomically() {
        UUID orderId = UUID.randomUUID();
        OrderAggregate order = createSampleOrder(orderId, "tenant-test-1", "idemp-it-1");

        OrderAggregate savedOrder = orderRepositoryAdapter.save(order);

        assertNotNull(savedOrder);
        assertEquals(orderId, savedOrder.id());

        List<OrderStatusHistoryJpaEntity> historyList = springDataOrderStatusHistoryJpaRepository.findByOrderIdOrderByChangedAtAsc(orderId);
        assertEquals(1, historyList.size());
        assertEquals("CREATED", historyList.getFirst().getToStatus());
        assertNull(historyList.getFirst().getFromStatus());
    }

    @Test
    @DisplayName("Should throw IdempotencyKeyConflictException on duplicate tenant and idempotency key")
    void shouldThrowIdempotencyConflictOnDuplicateKey() {
        UUID order1Id = UUID.randomUUID();
        UUID order2Id = UUID.randomUUID();
        String tenantId = "tenant-test-dup";
        String idempotencyKey = "idemp-dup-key";

        OrderAggregate order1 = createSampleOrder(order1Id, tenantId, idempotencyKey);
        OrderAggregate order2 = createSampleOrder(order2Id, tenantId, idempotencyKey);

        orderRepositoryAdapter.save(order1);

        assertThrows(IdempotencyKeyConflictException.class, () -> orderRepositoryAdapter.save(order2));
    }

    @Test
    @DisplayName("Should query recent summaries via keyset pagination")
    void shouldQuerySummariesViaKeysetPagination() {
        UUID orderId = UUID.randomUUID();
        String tenantId = "tenant-test-summary";
        OrderAggregate order = createSampleOrder(orderId, tenantId, "idemp-summary-1");

        orderRepositoryAdapter.save(order);

        List<OrderSummaryDTO> summaries = orderRepositoryAdapter.findRecentSummaries(
                tenantId,
                new OrderStatus.Created(),
                Instant.now().plusSeconds(60),
                10
        );

        assertFalse(summaries.isEmpty());
        assertEquals(orderId, summaries.getFirst().id());
        assertEquals("EUR", summaries.getFirst().totalAmount().currency());
    }

    private OrderAggregate createSampleOrder(UUID orderId, String tenantId, String idempotencyKey) {
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                "PROD-TEST-1",
                "Organic Sourdough Bread",
                2,
                new Money(new BigDecimal("3.50"), "EUR")
        );
        return OrderAggregate.create(orderId, tenantId, UUID.randomUUID(), idempotencyKey, List.of(item));
    }
}