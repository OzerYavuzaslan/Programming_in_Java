package com.ozeryavuzaslan.orderservice.model;

import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "failed_order_stocks", indexes = {
                @Index(name = "stockid_index", columnList = "stockid"),
                @Index(name = "reserve_stockid_index", columnList = "reserveStockID"),
                @Index(name = "reserve_date_index", columnList = "reserveDate")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FailedOrderStock {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private long stockid;

    @Column(nullable = false)
    private long reserveStockID;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReserveType reserveType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "failed_order_id", nullable = false)
    private FailedOrder failedOrder;

    @Column(nullable = false)
    private boolean reserveRollbackStatus;
    private boolean stockRollbackStatus;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reserveDate;

    @UpdateTimestamp
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}
