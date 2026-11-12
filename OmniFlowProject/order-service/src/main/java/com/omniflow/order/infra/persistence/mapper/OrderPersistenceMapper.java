package com.omniflow.order.infra.persistence.mapper;

import com.omniflow.order.domain.model.*;
import com.omniflow.order.infra.persistence.entity.OrderItemJpaEntity;
import com.omniflow.order.infra.persistence.entity.OrderJpaEntity;
import com.omniflow.order.infra.persistence.entity.OrderStatusHistoryJpaEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class OrderPersistenceMapper {
    public OrderJpaEntity toNewEntity(Order order) {
        OrderJpaEntity orderJpaEntity = getOrderJpaEntity(order);

        if (order.orderItemList() != null) {
            for (OrderItem item : order.orderItemList()) {
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

    public Order toDomain(OrderJpaEntity orderJpaEntity) {
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

        return Order.reconstitute(
                orderJpaEntity.getId(),
                orderJpaEntity.getTenantId(),
                orderJpaEntity.getCustomerId(),
                orderJpaEntity.getIdempotencyKey(),
                orderItemList,
                orderStatus,
                totalAmount);
    }

    public void applyChanges(Order order, OrderJpaEntity orderJpaEntity) {
        orderJpaEntity.setStatus(OrderStatusCodec.toCode(order.orderStatus()));
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

    private OrderJpaEntity getOrderJpaEntity(Order order) {
        OrderJpaEntity orderJpaEntity = OrderJpaEntity.getNewOrderJpaEntity();
        orderJpaEntity.setId(order.id());
        orderJpaEntity.setTenantId(order.tenantId());
        orderJpaEntity.setCustomerId(order.customerId());
        orderJpaEntity.setIdempotencyKey(order.idempotencyKey());
        orderJpaEntity.setStatus(OrderStatusCodec.toCode(order.orderStatus()));
        orderJpaEntity.setTotalAmount(order.totalAmount().amount());
        orderJpaEntity.setCurrency(order.totalAmount().currency());

        return orderJpaEntity;
    }
}