package com.ozeryavuzaslan.orderservice.model;

import com.ozeryavuzaslan.orderservice.model.enums.PaymentRollbackState;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackPhase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "failed_orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"orderid", "paymentid"})
},
        indexes = {
                @Index(name = "orderid_index", columnList = "orderid"),
                @Index(name = "paymentid_index", columnList = "paymentid"),
                @Index(name = "order_date_index", columnList = "orderDate"),
                @Index(name = "composite_index_1", columnList = "orderid, paymentid")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FailedOrder {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true, nullable = false)
    private long orderid;
    private long paymentid;

    @OneToMany(mappedBy = "failedOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FailedOrderStock> failedOrderStockList;

    @Column(nullable = false)
    private boolean orderRollbackStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentRollbackState paymentRollbackState;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RollbackPhase rollbackPhase;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @UpdateTimestamp
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}
