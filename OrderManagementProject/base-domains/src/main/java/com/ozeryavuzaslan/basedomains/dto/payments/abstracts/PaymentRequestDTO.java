package com.ozeryavuzaslan.basedomains.dto.payments.abstracts;

import com.ozeryavuzaslan.basedomains.dto.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.enums.MonetaryUnitType;
import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class PaymentRequestDTO {
    private long orderid;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private double taxRate;
    private double totalPrice;
    private double totalPriceWithoutTax;
    private CurrencyType currencyType;
    private MonetaryUnitType monetaryUnitType;
    private PaymentProviderType paymentProviderType;

    public abstract PaymentProviderType getPaymentProviderType();
}
