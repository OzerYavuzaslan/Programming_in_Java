package com.omniflow.order.application.service;

import com.omniflow.common.result.Result;
import com.omniflow.order.application.command.CancelOrderCommand;
import com.omniflow.order.application.command.CreateOrderCommand;
import com.omniflow.order.application.dto.OrderCreationResultDTO;
import com.omniflow.order.application.exception.OrderNotFoundException;
import com.omniflow.order.application.port.in.CancelOrderUseCase;
import com.omniflow.order.application.port.in.CreateOrderUseCase;
import com.omniflow.order.application.port.in.GetOrderQuery;
import com.omniflow.order.application.port.in.ListOrdersQuery;
import com.omniflow.order.domain.model.*;
import com.omniflow.order.domain.port.out.IdGeneratorPort;
import com.omniflow.order.domain.port.out.IdempotencyKeyConflictException;
import com.omniflow.order.domain.port.out.OrderRepositoryPort;
import com.omniflow.order.domain.port.out.OrderSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.omniflow.order.domain.constant.OrderErrorMessages.*;

@Service
@RequiredArgsConstructor
public class OrderApplicationService implements CreateOrderUseCase, CancelOrderUseCase, GetOrderQuery, ListOrdersQuery {
    private final Clock clock;
    private final IdGeneratorPort idGeneratorPort;
    private final OrderRepositoryPort orderRepositoryPort;

    @Qualifier("writeTransactionTemplate")
    private final TransactionTemplate writeTransactionTemplate;

    @Qualifier("readOnlyTransactionTemplate")
    private final TransactionTemplate readOnlyTransactionTemplate;

    @Override
    @Transactional
    public Result<OrderAggregate, OrderError> cancelOrder(CancelOrderCommand cancelOrderCommand) {
        Objects.requireNonNull(cancelOrderCommand, MSG_CANCEL_COMMAND_NULL);
        OrderAggregate orderAggregate = orderRepositoryPort.findById(cancelOrderCommand.orderId())
                .filter(aggregate -> aggregate.tenantId().equals(cancelOrderCommand.tenantId()))
                .orElseThrow(() -> new OrderNotFoundException(cancelOrderCommand.tenantId(), cancelOrderCommand.orderId()));

        return orderAggregate.cancel(cancelOrderCommand.reason()).map(orderRepositoryPort::save);
    }

    @Override
    public OrderCreationResultDTO createOrder(CreateOrderCommand createOrderCommand) {
        Objects.requireNonNull(createOrderCommand, MSG_CREATE_COMMAND_NULL);

        try {
            return writeTransactionTemplate.execute(transactionStatus -> {
                Optional<OrderAggregate> optionalOrderAggregate = orderRepositoryPort
                        .findByTenantIdAndIdempotencyKey(createOrderCommand.tenantId(),
                                createOrderCommand.idempotencyKey());

                if (optionalOrderAggregate.isPresent()) {
                    return new OrderCreationResultDTO(optionalOrderAggregate.get(), true);
                }

                List<OrderItem> orderItemList = createOrderCommand
                        .createOrderItemCommandList()
                        .stream()
                        .map(orderItemCommand -> new OrderItem(
                                idGeneratorPort.newId(),
                                orderItemCommand.productId(),
                                orderItemCommand.productName(),
                                orderItemCommand.quantity(),
                                new Money(orderItemCommand.unitPrice(), orderItemCommand.currency())
                        )).toList();

                OrderAggregate orderAggregate = OrderAggregate
                        .create(idGeneratorPort.newId(),
                                createOrderCommand.tenantId(),
                                createOrderCommand.customerId(),
                                createOrderCommand.idempotencyKey(),
                                orderItemList);

                OrderAggregate savedOrder = orderRepositoryPort.save(orderAggregate);
                return new OrderCreationResultDTO(savedOrder, false);
            });
        } catch (IdempotencyKeyConflictException ex) {
            return readOnlyTransactionTemplate.execute(status ->
                    orderRepositoryPort.findByTenantIdAndIdempotencyKey(
                                    createOrderCommand.tenantId(), createOrderCommand.idempotencyKey()
                            )
                            .map(existingOrder -> new OrderCreationResultDTO(existingOrder, true))
                            .orElseThrow(() -> new IllegalStateException(
                                    String.format(MSG_IDEMPOTENCY_REPLAY_LOAD_FAILED, createOrderCommand.idempotencyKey()), ex)));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OrderAggregate getOrder(String tenantId, UUID orderId) {
        Objects.requireNonNull(orderId, MSG_ORDER_ID_NULL);
        Objects.requireNonNull(tenantId, MSG_TENANT_ID_NULL);

        return orderRepositoryPort.findById(orderId)
                .filter(orderAggregate -> orderAggregate.tenantId().equals(tenantId))
                .orElseThrow(() -> new OrderNotFoundException(tenantId, orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderSummaryDTO> listOrders(String tenantId, OrderStatus orderStatus, Instant createdBefore, int limit) {
        validateListOrdersCriteria(tenantId, limit);

        if (Objects.isNull(createdBefore)) {
            createdBefore = Instant.now(clock);
        }

        return orderRepositoryPort.findRecentSummaries(tenantId, orderStatus, createdBefore, limit);
    }

    private void validateListOrdersCriteria(String tenantId, int limit) {
        if (Objects.isNull(tenantId) || tenantId.isBlank()) {
            throw new IllegalArgumentException(MSG_TENANT_ID_BLANK);
        }

        if (limit < 1 || limit > 100) {
            throw new IllegalArgumentException(MSG_LIMIT_OUT_OF_RANGE + limit);
        }
    }
}