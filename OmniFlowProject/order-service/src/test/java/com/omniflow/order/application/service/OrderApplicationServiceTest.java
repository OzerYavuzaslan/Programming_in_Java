package com.omniflow.order.application.service;

import com.omniflow.common.result.Result;
import com.omniflow.order.application.command.CancelOrderCommand;
import com.omniflow.order.application.command.CreateOrderCommand;
import com.omniflow.order.application.command.CreateOrderItemCommand;
import com.omniflow.order.application.dto.OrderCreationResultDTO;
import com.omniflow.order.application.exception.OrderNotFoundException;
import com.omniflow.order.domain.model.*;
import com.omniflow.order.domain.port.out.IdGeneratorPort;
import com.omniflow.order.domain.port.out.IdempotencyKeyConflictException;
import com.omniflow.order.domain.port.out.OrderRepositoryPort;
import com.omniflow.order.domain.port.out.OrderSummaryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderApplicationServiceTest {
    private static final String TENANT_ID = "STORE_1";
    private static final String IDEMPOTENCY_KEY = "IDEMP_KEY_001";
    private static final String CURRENCY = "EUR";
    private static final Instant FIXED_NOW = Instant.parse("2026-11-19T10:00:00Z");

    @Mock
    private Clock clock;

    @Mock
    private IdGeneratorPort idGeneratorPort;

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Mock
    private TransactionTemplate writeTransactionTemplate;

    @Mock
    private TransactionTemplate readOnlyTransactionTemplate;

    @InjectMocks
    private OrderApplicationService orderApplicationService;

    private UUID defaultOrderAggregateId;
    private OrderAggregate defaultOrderAggregate;

    @BeforeEach
    void setUp() {
        TransactionStatus transactionStatus = mock(TransactionStatus.class);

        lenient().when(writeTransactionTemplate.execute(any())).thenAnswer(invocation -> {
            TransactionCallback<?> transactionCallback = invocation.getArgument(0);
            return transactionCallback.doInTransaction(transactionStatus);
        });

        lenient().when(readOnlyTransactionTemplate.execute(any())).thenAnswer(invocation -> {
            TransactionCallback<?> transactionCallback = invocation.getArgument(0);
            return transactionCallback.doInTransaction(transactionStatus);
        });

        lenient().when(clock.instant()).thenReturn(FIXED_NOW);
        lenient().when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        defaultOrderAggregateId = UUID.randomUUID();
        defaultOrderAggregate = buildSampleOrderAggregate(defaultOrderAggregateId, new OrderStatus.Created());
    }

    private OrderAggregate buildSampleOrderAggregate(UUID orderId, OrderStatus orderStatus) {
        OrderItem orderItem = new OrderItem(
                UUID.randomUUID(),
                "PROD_001",
                "Product 1",
                2,
                new Money(new BigDecimal("10.00"), CURRENCY)
        );
        return new OrderAggregate(
                orderId,
                TENANT_ID,
                UUID.randomUUID(),
                IDEMPOTENCY_KEY,
                List.of(orderItem),
                orderStatus,
                new Money(new BigDecimal("20.00"), CURRENCY),
                List.of()
        );
    }

    @Test
    @DisplayName("createOrder - should successfully create new order when not exists")
    void createOrder_shouldCreateNewOrder_whenNotExists() {
        UUID orderId = UUID.randomUUID();

        when(idGeneratorPort.newId()).thenReturn(orderId);
        when(orderRepositoryPort.findByTenantIdAndIdempotencyKey(TENANT_ID, IDEMPOTENCY_KEY))
                .thenReturn(Optional.empty());
        when(orderRepositoryPort.save(any(OrderAggregate.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateOrderItemCommand createOrderItemCommand = new CreateOrderItemCommand(
                "PROD_001", "Product 1", 2, new BigDecimal("10.00"), CURRENCY
        );
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(
                TENANT_ID, UUID.randomUUID(), IDEMPOTENCY_KEY, List.of(createOrderItemCommand)
        );

        OrderCreationResultDTO orderCreationResultDTO = orderApplicationService.createOrder(createOrderCommand);

        assertNotNull(orderCreationResultDTO);
        assertFalse(orderCreationResultDTO.replayed());
        assertEquals(orderId, orderCreationResultDTO.orderAggregate().id());
        verify(orderRepositoryPort, times(1)).save(any(OrderAggregate.class));
    }

    @Test
    @DisplayName("createOrder - should replay existing order without saving when already exists")
    void createOrder_shouldReplayExistingOrder_whenAlreadyExists() {
        when(orderRepositoryPort.findByTenantIdAndIdempotencyKey(TENANT_ID, IDEMPOTENCY_KEY))
                .thenReturn(Optional.of(defaultOrderAggregate));

        CreateOrderItemCommand createOrderItemCommand = new CreateOrderItemCommand(
                "PROD_001", "Product 1", 2, new BigDecimal("10.00"), CURRENCY
        );
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(
                TENANT_ID, UUID.randomUUID(), IDEMPOTENCY_KEY, List.of(createOrderItemCommand)
        );

        OrderCreationResultDTO orderCreationResultDTO = orderApplicationService.createOrder(createOrderCommand);

        assertNotNull(orderCreationResultDTO);
        assertTrue(orderCreationResultDTO.replayed());
        assertEquals(defaultOrderAggregateId, orderCreationResultDTO.orderAggregate().id());
        verify(orderRepositoryPort, never()).save(any(OrderAggregate.class));
    }

    @Test
    @DisplayName("createOrder - should fallback to readOnly transaction on concurrent conflict")
    void createOrder_shouldFallbackToReadOnlyTransaction_onConcurrentConflict() {
        when(idGeneratorPort.newId()).thenReturn(UUID.randomUUID());
        when(orderRepositoryPort.findByTenantIdAndIdempotencyKey(TENANT_ID, IDEMPOTENCY_KEY))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(defaultOrderAggregate));

        when(orderRepositoryPort.save(any(OrderAggregate.class)))
                .thenThrow(new IdempotencyKeyConflictException(TENANT_ID, IDEMPOTENCY_KEY));

        CreateOrderItemCommand createOrderItemCommand = new CreateOrderItemCommand(
                "PROD_001", "Product 1", 2, new BigDecimal("10.00"), CURRENCY
        );
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(
                TENANT_ID, UUID.randomUUID(), IDEMPOTENCY_KEY, List.of(createOrderItemCommand)
        );

        OrderCreationResultDTO orderCreationResultDTO = orderApplicationService.createOrder(createOrderCommand);

        assertNotNull(orderCreationResultDTO);
        assertTrue(orderCreationResultDTO.replayed());
        assertEquals(defaultOrderAggregateId, orderCreationResultDTO.orderAggregate().id());
        verify(orderRepositoryPort, times(1)).save(any(OrderAggregate.class));
    }

    @Test
    @DisplayName("cancelOrder - should cancel order when in Created status")
    void cancelOrder_shouldCancel_whenInCreatedStatus() {
        when(orderRepositoryPort.findById(defaultOrderAggregateId)).thenReturn(Optional.of(defaultOrderAggregate));
        when(orderRepositoryPort.save(any(OrderAggregate.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(TENANT_ID, defaultOrderAggregateId, "Customer requested");
        Result<OrderAggregate, OrderError> result = orderApplicationService.cancelOrder(cancelOrderCommand);

        assertTrue(result.isSuccess());
        OrderAggregate cancelledOrder = ((Result.Success<OrderAggregate, OrderError>) result).value();
        assertInstanceOf(OrderStatus.Cancelled.class, cancelledOrder.orderStatus());
    }

    @Test
    @DisplayName("cancelOrder - should fail without saving when order is in Paid status")
    void cancelOrder_shouldFail_whenInPaidStatus() {
        UUID paidOrderId = UUID.randomUUID();
        OrderAggregate paidOrderAggregate = buildSampleOrderAggregate(paidOrderId, new OrderStatus.Paid());

        when(orderRepositoryPort.findById(paidOrderId)).thenReturn(Optional.of(paidOrderAggregate));

        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(TENANT_ID, paidOrderId, "Customer requested");
        Result<OrderAggregate, OrderError> result = orderApplicationService.cancelOrder(cancelOrderCommand);

        assertTrue(result.isFailure());
        verify(orderRepositoryPort, never()).save(any(OrderAggregate.class));
    }

    @Test
    @DisplayName("cancelOrder - should throw OrderNotFoundException on tenant mismatch")
    void cancelOrder_shouldThrowException_onTenantMismatch() {
        when(orderRepositoryPort.findById(defaultOrderAggregateId)).thenReturn(Optional.of(defaultOrderAggregate));

        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand("OTHER_TENANT", defaultOrderAggregateId, "Customer requested");
        assertThrows(OrderNotFoundException.class, () -> orderApplicationService.cancelOrder(cancelOrderCommand));
        verify(orderRepositoryPort, never()).save(any(OrderAggregate.class));
    }

    @Test
    @DisplayName("getOrder - should return order when tenant matches")
    void getOrder_shouldReturnOrder_whenTenantMatches() {
        when(orderRepositoryPort.findById(defaultOrderAggregateId)).thenReturn(Optional.of(defaultOrderAggregate));

        OrderAggregate orderAggregate = orderApplicationService.getOrder(TENANT_ID, defaultOrderAggregateId);
        assertNotNull(orderAggregate);
        assertEquals(defaultOrderAggregateId, orderAggregate.id());
    }

    @Test
    @DisplayName("getOrder - should throw OrderNotFoundException when not found")
    void getOrder_shouldThrowException_whenNotFound() {
        UUID nonExistingOrderId = UUID.randomUUID();
        when(orderRepositoryPort.findById(nonExistingOrderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderApplicationService.getOrder(TENANT_ID, nonExistingOrderId));
    }

    @Test
    @DisplayName("listOrders - should validate criteria and call repository")
    void listOrders_shouldValidateAndCallRepository() {
        when(orderRepositoryPort.findRecentSummaries(eq(TENANT_ID), any(), eq(FIXED_NOW), eq(20)))
                .thenReturn(List.of(new OrderSummaryDTO(UUID.randomUUID(), new Money(BigDecimal.TEN, CURRENCY), FIXED_NOW)));

        List<OrderSummaryDTO> orderSummaryDTOList = orderApplicationService.listOrders(TENANT_ID, null, null, 20);

        assertNotNull(orderSummaryDTOList);
        assertEquals(1, orderSummaryDTOList.size());
        verify(orderRepositoryPort, times(1)).findRecentSummaries(eq(TENANT_ID), any(), eq(FIXED_NOW), eq(20));
    }

    @Test
    @DisplayName("listOrders - should throw IllegalArgumentException on invalid limit")
    void listOrders_shouldThrowException_onInvalidLimit() {
        assertThrows(IllegalArgumentException.class, () -> orderApplicationService.listOrders(TENANT_ID, null, null, 0));
        assertThrows(IllegalArgumentException.class, () -> orderApplicationService.listOrders(TENANT_ID, null, null, 101));
    }
}