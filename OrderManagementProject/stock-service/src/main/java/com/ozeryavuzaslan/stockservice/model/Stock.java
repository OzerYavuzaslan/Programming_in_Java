package com.ozeryavuzaslan.stockservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "stocks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"stockCode", "category_id"})
        },
        indexes = {
                @Index(name = "product_name_index", columnList = "stockCode"),
                @Index(name = "stock_add_date_index", columnList = "addDate"),
                @Index(name = "stock_update_date_index", columnList = "updateDate")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID stockCode;

    @Column(unique = true, nullable = false)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(nullable = false, unique = true, name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}