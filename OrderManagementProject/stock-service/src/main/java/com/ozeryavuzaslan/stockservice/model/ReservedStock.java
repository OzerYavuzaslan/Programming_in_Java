package com.ozeryavuzaslan.stockservice.model;

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

@Table(name = "reserved_stocks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"orderid", "stock_id"})
        },
        indexes = {
                @Index(name = "stock_id_index", columnList = "stock_id"),
                @Index(name = "orderid_index", columnList = "orderid"),
                @Index(name = "composite_order_stock_index", columnList = "orderid, stock_id"),
                @Index(name = "stock_reserve_date_index", columnList = "stockReserveDate"),
                @Index(name = "stock_reserve_update_date_index", columnList = "stockReserveUpdateDate")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservedStock {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private long orderid;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReserveType reserveType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime stockReserveDate;

    @UpdateTimestamp
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime stockReserveUpdateDate;
}
