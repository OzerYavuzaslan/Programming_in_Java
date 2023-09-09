package com.ozeryavuzaslan.paymentservice.model;

import com.ozeryavuzaslan.basedomains.dto.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.enums.MonetaryUnitType;
import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "payment_invoices",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"orderid", "payment_id", "balanceTransactionId"})
        },
        indexes = {
                @Index(name = "order_id_index", columnList = "orderid"),
                @Index(name = "user_id_index", columnList = "userid"),
                @Index(name = "user_email_index", columnList = "email"),
                @Index(name = "user_index", columnList = "name, surname"),
                @Index(name = "payment_id_index", columnList = "payment_id"),
                @Index(name = "balance_transaction_id_index", columnList = "balanceTransactionId")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInvoice {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, unique = true)
    private long orderid;

    @Column(nullable = false, unique = true)
    private String balanceTransactionId;

    @Column(name = "payment_id", nullable = false, unique = true)
    private String paymentid;

    @Column(nullable = false)
    private String userid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private double taxRate;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private double totalPriceWithoutTax;

    @Column(nullable = false)
    private String paymentStatus;

    @Column(nullable = false)
    private String receiptUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MonetaryUnitType monetaryUnitType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentProviderType paymentProviderType;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;
}
