package com.ozeryavuzaslan.revenueservice.model;

import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "tax_rates",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"year", "month", "taxRateType"}, name = "unique_year_month_taxRateType")
        },
        indexes = {
                @Index(name = "year_index", columnList = "year"),
                @Index(name = "month_index", columnList = "month"),
                @Index(name = "tax_rate_type_index", columnList = "taxRateType"),
                @Index(name = "year_month_index", columnList = "month, year, taxRateType")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private double rate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaxRateType taxRateType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;

    @CreationTimestamp
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}
