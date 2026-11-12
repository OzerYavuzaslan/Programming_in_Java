package com.omniflow.order.infra.persistence.entity;

import com.omniflow.common.persistence.BaseJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class OrderJpaEntity extends BaseJpaEntity {
    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, updatable = false, length = 64)
    private String tenantId;

    @Column(name = "customer_id", nullable = false, updatable = false)
    private UUID customerId;

    @Column(name = "idempotency_key", nullable = false, updatable = false, length = 128)
    private String idempotencyKey;

    @ToString.Include
    @Column(name = "status", nullable = false, length = 32)
    private String status;

    @Column(name = "total_amount", nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal totalAmount;

    @Column(name = "currency", nullable = false, updatable = false, length = 3)
    private String currency;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemJpaEntity> orderItemList = new ArrayList<>();

    public void addOrderItem(OrderItemJpaEntity orderItemJpaEntity) {
        this.orderItemList.add(orderItemJpaEntity);
        orderItemJpaEntity.setOrder(this);
    }

    public void removeOrderItem(OrderItemJpaEntity orderItemJpaEntity) {
        this.orderItemList.remove(orderItemJpaEntity);
        orderItemJpaEntity.setOrder(null);
    }

    public static OrderJpaEntity getNewOrderJpaEntity() {
        return new OrderJpaEntity();
    }
}