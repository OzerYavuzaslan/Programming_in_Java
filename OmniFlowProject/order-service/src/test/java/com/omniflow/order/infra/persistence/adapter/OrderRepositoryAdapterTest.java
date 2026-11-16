package com.omniflow.order.infra.persistence.adapter;

import com.omniflow.order.domain.model.Money;
import com.omniflow.order.domain.model.OrderAggregate;
import com.omniflow.order.domain.model.OrderItem;
import com.omniflow.order.domain.model.OrderStatus;
import com.omniflow.order.domain.port.out.IdempotencyKeyConflictException;
import com.omniflow.order.domain.port.out.OrderSummaryDTO;
import com.omniflow.order.infra.persistence.entity.OrderJpaEntity;
import com.omniflow.order.infra.persistence.entity.OrderStatusHistoryJpaEntity;
import com.omniflow.order.infra.persistence.mapper.OrderPersistenceMapper;
import com.omniflow.order.infra.persistence.repository.OrderSummaryProjectionDTO;
import com.omniflow.order.infra.persistence.repository.SpringDataOrderJpaRepository;
import com.omniflow.order.infra.persistence.repository.SpringDataOrderStatusHistoryJpaRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Limit;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrderRepositoryAdapter Unit Tests")
class OrderRepositoryAdapterTest {

    @Mock
    private OrderPersistenceMapper mapper;

    @Mock
    private SpringDataOrderJpaRepository orderJpaRepository;

    @Mock
    private SpringDataOrderStatusHistoryJpaRepository historyJpaRepository;

    @InjectMocks
    private OrderRepositoryAdapter adapter;

    @Test
    @DisplayName("Should insert new order and save history when order does not exist")
    void shouldInsertNewOrderWhenDoesNotExist() {
        UUID orderId = UUID.randomUUID();
        OrderAggregate domainOrder = createSampleOrder(orderId);
        OrderJpaEntity newEntity = OrderJpaEntity.getNewOrderJpaEntity();

        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.empty());
        when(mapper.toNewEntity(domainOrder)).thenReturn(newEntity);
        when(orderJpaRepository.saveAndFlush(newEntity)).thenReturn(newEntity);
        when(mapper.toHistoryEntities(eq(orderId), any())).thenReturn(List.of(OrderStatusHistoryJpaEntity.getNewOrderStatusHistoryJpaEntity()));
        when(mapper.toDomain(newEntity)).thenReturn(domainOrder);

        OrderAggregate result = adapter.save(domainOrder);

        assertNotNull(result);
        verify(orderJpaRepository).saveAndFlush(newEntity);
        verify(historyJpaRepository).saveAll(any());
        verify(mapper, never()).applyChanges(any(), any());
    }

    @Test
    @DisplayName("Should apply changes to existing entity when order already exists")
    void shouldUpdateExistingOrderWhenAlreadyExists() {
        UUID orderId = UUID.randomUUID();
        OrderAggregate domainOrder = createSampleOrder(orderId);
        OrderJpaEntity existingEntity = OrderJpaEntity.getNewOrderJpaEntity();

        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.of(existingEntity));
        when(mapper.toDomain(existingEntity)).thenReturn(domainOrder);

        OrderAggregate result = adapter.save(domainOrder);

        assertNotNull(result);
        verify(mapper).applyChanges(domainOrder, existingEntity);
        verify(orderJpaRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Should translate DataIntegrityViolationException to IdempotencyKeyConflictException on unique violation")
    void shouldTranslateToIdempotencyConflictException() {
        UUID orderId = UUID.randomUUID();
        OrderAggregate domainOrder = createSampleOrder(orderId);
        OrderJpaEntity newEntity = OrderJpaEntity.getNewOrderJpaEntity();

        ConstraintViolationException cve = new ConstraintViolationException(
                "Unique index violation",
                new SQLException("duplicate key"),
                "uq_orders_tenant_idempotency_key"
        );
        DataIntegrityViolationException dive = new DataIntegrityViolationException("Conflict", cve);

        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.empty());
        when(mapper.toNewEntity(domainOrder)).thenReturn(newEntity);
        when(orderJpaRepository.saveAndFlush(newEntity)).thenThrow(dive);

        IdempotencyKeyConflictException exception = assertThrows(
                IdempotencyKeyConflictException.class,
                () -> adapter.save(domainOrder)
        );

        assertEquals("tenant-amsterdam", exception.getTenantId());
        assertEquals("idemp-key-1", exception.getIdempotencyKey());
    }

    @Test
    @DisplayName("Should rethrow DataIntegrityViolationException if violation is not related to idempotency")
    void shouldRethrowExceptionWhenNotIdempotencyViolation() {
        UUID orderId = UUID.randomUUID();
        OrderAggregate domainOrder = createSampleOrder(orderId);
        OrderJpaEntity newEntity = OrderJpaEntity.getNewOrderJpaEntity();

        ConstraintViolationException cve = new ConstraintViolationException(
                "Check constraint violation",
                new SQLException("check violation"),
                "chk_orders_total_amount_positive"
        );
        DataIntegrityViolationException dive = new DataIntegrityViolationException("Constraint violation", cve);

        when(orderJpaRepository.findById(orderId)).thenReturn(Optional.empty());
        when(mapper.toNewEntity(domainOrder)).thenReturn(newEntity);
        when(orderJpaRepository.saveAndFlush(newEntity)).thenThrow(dive);

        assertThrows(DataIntegrityViolationException.class, () -> adapter.save(domainOrder));
    }

    @Test
    @DisplayName("Should fail-fast when orderAggregate is null")
    void shouldThrowExceptionWhenOrderIsNull() {
        assertThrows(NullPointerException.class, () -> adapter.save(null));
    }

    @Test
    @DisplayName("Should find recent summaries and map to domain OrderSummaryDTO")
    void shouldFindRecentSummaries() {
        Instant now = Instant.now();
        OrderSummaryProjectionDTO projection = new OrderSummaryProjectionDTO(
                UUID.randomUUID(),
                new BigDecimal("50.00"),
                "EUR",
                now
        );

        when(orderJpaRepository.findSummaries(eq("tenant-amsterdam"), eq("PAID"), eq(now), eq(Limit.of(10))))
                .thenReturn(List.of(projection));

        List<OrderSummaryDTO> summaries = adapter.findRecentSummaries("tenant-amsterdam", new OrderStatus.Paid(), now, 10);

        assertEquals(1, summaries.size());
        assertEquals(projection.id(), summaries.get(0).id());
        assertEquals(new BigDecimal("50.00"), summaries.get(0).totalAmount().amount());
        assertEquals("EUR", summaries.get(0).totalAmount().currency());
        assertEquals(now, summaries.get(0).createdAt());
    }

    private OrderAggregate createSampleOrder(UUID orderId) {
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                "PROD-1",
                "Product 1",
                1,
                new Money(new BigDecimal("10.00"), "EUR")
        );
        return OrderAggregate.create(orderId, "tenant-amsterdam", UUID.randomUUID(), "idemp-key-1", List.of(item));
    }
}