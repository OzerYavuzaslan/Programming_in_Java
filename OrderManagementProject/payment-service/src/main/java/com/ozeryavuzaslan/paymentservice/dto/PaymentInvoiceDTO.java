package com.ozeryavuzaslan.paymentservice.dto;

import com.ozeryavuzaslan.basedomains.dto.payments.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInvoiceDTO implements Serializable {
    private long id;
    private long orderid;
    private String balanceTransactionId;
    private String paymentid;
    private String refundid;
    private String userid;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private double taxRate;
    private double totalPrice;
    private double totalPriceWithoutTax;
    private double totalPriceWithDiscount;
    private double totalPriceWithDiscountWithoutTax;
    private double refundedAmount;
    private double refundRequestAmount;
    private String receiptUrl;
    private CurrencyType currencyType;
    private PaymentStatus paymentStatus;
    private MonetaryUnitType monetaryUnitType;
    private PaymentProviderType paymentProviderType;
    private PaymentType paymentType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundDate;
}
