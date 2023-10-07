package com.ozeryavuzaslan.orderservice.model;

import com.ozeryavuzaslan.basedomains.dto.orders.enums.OrderStatusType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.MonetaryUnitType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentStatus;
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
import java.util.List;

@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"paymentid"})
},
        indexes = {
                @Index(name = "user_email_index", columnList = "email"),
                @Index(name = "user_index", columnList = "name, surname"),
                @Index(name = "order_date_index", columnList = "orderDate"),
                @Index(name = "update_date_index", columnList = "updateDate"),
                @Index(name = "payment_id_index", columnList = "paymentid")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderStock> orderStockList;

    private double taxRate;
    private double totalPrice;
    private double totalPriceWithoutTax;
    private double totalPriceWithDiscount;
    private double totalPriceWithDiscountWithoutTax;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address1;

    @Column(columnDefinition = "TEXT")
    private String address2;

    @Column(unique = true, nullable = true)
    private String paymentid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReserveType reserveType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentProviderType paymentProviderType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MonetaryUnitType monetaryUnitType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusType orderStatusType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @UpdateTimestamp
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}