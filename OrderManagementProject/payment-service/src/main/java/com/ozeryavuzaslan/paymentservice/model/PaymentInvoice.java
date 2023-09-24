package com.ozeryavuzaslan.paymentservice.model;

import com.ozeryavuzaslan.basedomains.dto.payments.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "payment_invoices",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"orderid", "payment_id", "balanceTransactionId", "refund_id"})
        },
        indexes = {
                @Index(name = "order_id_index", columnList = "orderid"),
                @Index(name = "user_id_index", columnList = "userid"),
                @Index(name = "user_email_index", columnList = "email"),
                @Index(name = "user_index", columnList = "name, surname"),
                @Index(name = "payment_id_index", columnList = "payment_id"),
                @Index(name = "balance_transaction_id_index", columnList = "balanceTransactionId"),
                @Index(name = "refund_id_index", columnList = "refund_id")
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

    @Column(name = "refund_id", unique = true)
    private String refundid;

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
    private double refundedAmount = 0.0D;

    @Column(nullable = false)
    private double refundRequestAmount = 0.0D;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String receiptUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MonetaryUnitType monetaryUnitType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentProviderType paymentProviderType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundDate;
}
