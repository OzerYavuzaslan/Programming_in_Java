package com.ozeryavuzaslan.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "order_stocks",
        indexes = {
                @Index(name = "product_code_index", columnList = "productCode"),
                @Index(name = "product_name_index", columnList = "productName"),
                @Index(name = "product_composite_index1", columnList = "productCode, productName")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStock {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private UUID productCode;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    private double price;

    private double discountAmount = 0.0D;
    private double discountPercentage = 0.0D;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime discountEndDate = null;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
