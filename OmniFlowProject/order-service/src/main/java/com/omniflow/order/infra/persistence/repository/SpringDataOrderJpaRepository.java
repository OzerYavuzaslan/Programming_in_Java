package com.omniflow.order.infra.persistence.repository;

import com.omniflow.order.infra.persistence.entity.OrderJpaEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link OrderJpaEntity}.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public interface SpringDataOrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
    @EntityGraph(attributePaths = {"orderItemList"})
    Optional<OrderJpaEntity> findWithOrderItemListById(UUID id);

    @EntityGraph(attributePaths = {"orderItemList"})
    Optional<OrderJpaEntity> findWithOrderItemListByTenantIdAndIdempotencyKey(String tenantId, String idempotencyKey);

    @Query("""
            SELECT new com.omniflow.order.infra.persistence.repository.OrderSummaryProjectionDTO(
                o.id,
                o.totalAmount,
                o.currency,
                o.createdAt
            )
            FROM OrderJpaEntity o
            WHERE o.tenantId = :tenantId
                AND o.status = :status
                AND o.createdAt < :createdBefore
            ORDER BY o.createdAt DESC
            """)
    List<OrderSummaryProjectionDTO> findSummaries(
            @Param("tenantId") String tenantId,
            @Param("status") String status,
            @Param("createdBefore") Instant createdBefore,
            Limit limit
    );
}