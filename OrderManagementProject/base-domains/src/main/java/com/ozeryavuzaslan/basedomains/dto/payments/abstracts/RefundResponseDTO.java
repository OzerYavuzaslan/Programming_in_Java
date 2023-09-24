package com.ozeryavuzaslan.basedomains.dto.payments.abstracts;

import com.ozeryavuzaslan.basedomains.dto.payments.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class RefundResponseDTO {
    private long id;
    private String name;
    private String email;
    private long orderid;
    private String surname;
    private String phoneNumber;
    private CurrencyType currencyType;
    private PaymentStatus paymentStatus;
    private MonetaryUnitType monetaryUnitType;
    private PaymentProviderType paymentProviderType;
    private double refundedAmount;
    private double refundRequestAmount;
    private PaymentType paymentType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundDate;
}
