package com.ozeryavuzaslan.basedomains.dto.payments.abstracts;

import com.ozeryavuzaslan.basedomains.dto.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.enums.MonetaryUnitType;
import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class PaymentResponseDTO implements Serializable {
    private long id;
    private String name;
    private String email;
    private long orderid;
    private double taxRate;
    private String surname;
    private double totalPrice;
    private String phoneNumber;
    private CurrencyType currencyType;
    private double totalPriceWithoutTax;
    private PaymentStatus paymentStatus;
    private MonetaryUnitType monetaryUnitType;
    private PaymentProviderType paymentProviderType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;
}
