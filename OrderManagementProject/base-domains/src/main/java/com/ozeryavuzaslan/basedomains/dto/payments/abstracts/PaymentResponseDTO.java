package com.ozeryavuzaslan.basedomains.dto.payments.abstracts;

import com.ozeryavuzaslan.basedomains.dto.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.enums.MonetaryUnitType;
import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;
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
public abstract class PaymentResponseDTO {
    private long id;
    private long orderid;
    private PaymentProviderType paymentProviderType;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private double taxRate;
    private double totalPrice;
    private double totalPriceWithoutTax;
    private String paymentStatus;
    private CurrencyType currencyType;
    private MonetaryUnitType monetaryUnitType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;
}
