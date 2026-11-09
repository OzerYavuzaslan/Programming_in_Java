package com.omniflow.order.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "order_items")
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemJpaEntity {
    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private OrderJpaEntity order;

    @Column(name = "product_id", nullable = false, updatable = false, length = 64)
    private String productId;

    @Column(name = "product_name", nullable = false, updatable = false, length = 255)
    private String productName;

    @Column(name = "quantity", nullable = false, updatable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal unitPrice;

    @Column(name = "currency", nullable = false, updatable = false, length = 3)
    private String currency;
}