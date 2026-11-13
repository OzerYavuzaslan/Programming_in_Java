package com.omniflow.order.infra.persistence.mapper;

import com.omniflow.order.domain.model.*;
import com.omniflow.order.infra.persistence.entity.OrderItemJpaEntity;
import com.omniflow.order.infra.persistence.entity.OrderJpaEntity;
import com.omniflow.order.infra.persistence.entity.OrderStatusHistoryJpaEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderPersistenceMapper {
    public OrderJpaEntity toNewEntity(OrderAggregate orderAggregate) {
        Objects.requireNonNull(orderAggregate, "OrderAggregate cannot be null");
        OrderJpaEntity orderJpaEntity = getOrderJpaEntity(orderAggregate);

        if (CollectionUtils.isNotEmpty(orderAggregate.orderItemList())) {
            for (OrderItem item : orderAggregate.orderItemList()) {
                OrderItemJpaEntity orderItemJpaEntity = OrderItemJpaEntity.getOrderItemJpaEntity();
                orderItemJpaEntity.setId(item.id());
                orderItemJpaEntity.setProductId(item.productId());
                orderItemJpaEntity.setProductName(item.productName());
                orderItemJpaEntity.setQuantity(item.quantity());
                orderItemJpaEntity.setUnitPrice(item.unitPrice().amount());
                orderItemJpaEntity.setCurrency(item.unitPrice().currency());
                orderJpaEntity.addOrderItem(orderItemJpaEntity);
            }
        }

        return orderJpaEntity;
    }

    public OrderAggregate toDomain(OrderJpaEntity orderJpaEntity) {
        List<OrderItem> orderItemList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(orderJpaEntity.getOrderItemList())) {
            orderItemList = orderJpaEntity.getOrderItemList()
                    .stream()
                    .map(item -> new OrderItem(
                            item.getId(),
                            item.getProductId(),
                            item.getProductName(),
                            item.getQuantity(),
                            new Money(item.getUnitPrice(), item.getCurrency()))).toList();
        }

        Money totalAmount = new Money(orderJpaEntity.getTotalAmount(), orderJpaEntity.getCurrency());
        OrderStatus orderStatus = OrderStatusCodec.fromCode(orderJpaEntity.getStatus());

        return OrderAggregate.reconstitute(
                orderJpaEntity.getId(),
                orderJpaEntity.getTenantId(),
                orderJpaEntity.getCustomerId(),
                orderJpaEntity.getIdempotencyKey(),
                orderItemList,
                orderStatus,
                totalAmount);
    }

    public void applyChanges(OrderAggregate orderAggregate, OrderJpaEntity orderJpaEntity) {
        orderJpaEntity.setStatus(OrderStatusCodec.toCode(orderAggregate.orderStatus()));
    }

    public List<OrderStatusHistoryJpaEntity> toHistoryEntities(UUID orderId, List<OrderStatusChange> orderStatusChangeList) {
        if (CollectionUtils.isEmpty(orderStatusChangeList)) {
            return Collections.emptyList();
        }

        return orderStatusChangeList
                .stream()
                .map(change -> {
                    OrderStatusHistoryJpaEntity newOrderStatusHistoryJpaEntity = OrderStatusHistoryJpaEntity.getNewOrderStatusHistoryJpaEntity();
                    newOrderStatusHistoryJpaEntity.setId(change.id());
                    newOrderStatusHistoryJpaEntity.setOrderId(orderId);
                    newOrderStatusHistoryJpaEntity.setFromStatus(change.fromStatus() == null ? null : OrderStatusCodec.toCode(change.fromStatus()));
                    newOrderStatusHistoryJpaEntity.setToStatus(OrderStatusCodec.toCode(change.toStatus()));
                    newOrderStatusHistoryJpaEntity.setReason(change.reason());
                    newOrderStatusHistoryJpaEntity.setChangedAt(change.changedAt());

                    return newOrderStatusHistoryJpaEntity;
                }).toList();
    }

    private OrderJpaEntity getOrderJpaEntity(OrderAggregate orderAggregate) {
        OrderJpaEntity orderJpaEntity = OrderJpaEntity.getNewOrderJpaEntity();
        orderJpaEntity.setId(orderAggregate.id());
        orderJpaEntity.setTenantId(orderAggregate.tenantId());
        orderJpaEntity.setCustomerId(orderAggregate.customerId());
        orderJpaEntity.setIdempotencyKey(orderAggregate.idempotencyKey());
        orderJpaEntity.setStatus(OrderStatusCodec.toCode(orderAggregate.orderStatus()));
        orderJpaEntity.setTotalAmount(orderAggregate.totalAmount().amount());
        orderJpaEntity.setCurrency(orderAggregate.totalAmount().currency());
        return orderJpaEntity;
    }
}
