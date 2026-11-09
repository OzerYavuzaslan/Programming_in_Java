package com.omniflow.order.infra.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "order_status_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrderStatusHistoryJpaEntity implements Persistable<UUID> {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "order_id", nullable = false, updatable = false)
    private UUID orderId;

    @Column(name = "from_status", updatable = false, length = 32)
    private String fromStatus;

    @Column(name = "to_status", nullable = false, updatable = false, length = 32)
    private String toStatus;

    @Column(name = "reason", updatable = false, length = 64)
    private String reason;

    @CreatedBy
    @Column(name = "changed_by", nullable = false, updatable = false, length = 64)
    private String changedBy;

    @Column(name = "changed_at", nullable = false, updatable = false)
    private Instant changedAt;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Transient
    @Override
    public boolean isNew() {
        return true;
    }
}