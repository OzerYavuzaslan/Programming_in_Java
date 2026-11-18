package com.omniflow.order.infra.persistence.adapter;

import com.omniflow.order.domain.model.Money;
import com.omniflow.order.domain.model.OrderAggregate;
import com.omniflow.order.domain.model.OrderStatus;
import com.omniflow.order.domain.port.out.IdempotencyKeyConflictException;
import com.omniflow.order.domain.port.out.OrderRepositoryPort;
import com.omniflow.order.domain.port.out.OrderSummaryDTO;
import com.omniflow.order.infra.persistence.entity.OrderJpaEntity;
import com.omniflow.order.infra.persistence.mapper.OrderPersistenceMapper;
import com.omniflow.order.infra.persistence.mapper.OrderStatusCodec;
import com.omniflow.order.infra.persistence.repository.SpringDataOrderJpaRepository;
import com.omniflow.order.infra.persistence.repository.SpringDataOrderStatusHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.omniflow.order.domain.constant.OrderErrorMessages.MSG_ORDER_NULL;

/**
 * Infrastructure persistence adapter implementing {@link OrderRepositoryPort}.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class OrderRepositoryAdapter implements OrderRepositoryPort {
    private final OrderPersistenceMapper orderPersistenceMapper;
    private final SpringDataOrderJpaRepository springDataOrderJpaRepository;
    private static final String UQ_IDEMPOTENCY_CONSTRAINT = "uq_orders_tenant_idempotency_key";
    private final SpringDataOrderStatusHistoryJpaRepository springDataOrderStatusHistoryJpaRepository;

    @Override
    public OrderAggregate save(OrderAggregate orderAggregate) {
        Objects.requireNonNull(orderAggregate, MSG_ORDER_NULL);
        Optional<OrderJpaEntity> existingEntityOpt = springDataOrderJpaRepository.findById(orderAggregate.id());

        OrderJpaEntity entityToPersist;

        if (existingEntityOpt.isEmpty()) {
            entityToPersist = orderPersistenceMapper.toNewEntity(orderAggregate);

            try {
                entityToPersist = springDataOrderJpaRepository.saveAndFlush(entityToPersist);
            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                if (isIdempotencyConstraintViolation(dataIntegrityViolationException)) {
                    throw new IdempotencyKeyConflictException(orderAggregate.tenantId(), orderAggregate.idempotencyKey());
                }

                throw dataIntegrityViolationException;
            }
        } else {
            entityToPersist = existingEntityOpt.get();
            orderPersistenceMapper.applyChanges(orderAggregate, entityToPersist);
        }

        if (CollectionUtils.isNotEmpty(orderAggregate.statusChangeList())) {
            springDataOrderStatusHistoryJpaRepository.saveAll(orderPersistenceMapper.toHistoryEntities(orderAggregate.id(), orderAggregate.statusChangeList()));
        }

        return orderPersistenceMapper.toDomain(entityToPersist);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderAggregate> findById(UUID orderId) {
        return springDataOrderJpaRepository.findWithOrderItemListById(orderId).map(orderPersistenceMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderAggregate> findByTenantIdAndIdempotencyKey(String tenantId, String idempotencyKey) {
        return springDataOrderJpaRepository.findWithOrderItemListByTenantIdAndIdempotencyKey(tenantId, idempotencyKey).map(orderPersistenceMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderSummaryDTO> findRecentSummaries(String tenantId, OrderStatus status, Instant createdBefore, int limit) {
        String statusCode = OrderStatusCodec.toCode(status);
        return springDataOrderJpaRepository.findSummaries(tenantId, statusCode, createdBefore, Limit.of(limit))
                .stream()
                .map(projection -> new OrderSummaryDTO(projection.id(),
                        new Money(projection.totalAmount(), projection.currency()), projection.createdAt()
                )).toList();
    }

    private boolean isIdempotencyConstraintViolation(DataIntegrityViolationException dataIntegrityViolationException) {
        Throwable exceptionCause = dataIntegrityViolationException.getCause();

        while (Objects.nonNull(exceptionCause)) {
            if (exceptionCause instanceof ConstraintViolationException constraintViolationException) {
                return UQ_IDEMPOTENCY_CONSTRAINT.equalsIgnoreCase(constraintViolationException.getConstraintName());
            }

            exceptionCause = exceptionCause.getCause();
        }

        return false;
    }
}