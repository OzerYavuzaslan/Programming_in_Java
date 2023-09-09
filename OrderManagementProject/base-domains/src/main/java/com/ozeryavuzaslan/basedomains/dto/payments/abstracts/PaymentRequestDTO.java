package com.ozeryavuzaslan.basedomains.dto.payments.abstracts;

import com.ozeryavuzaslan.basedomains.dto.enums.Currency;
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
    private long orderId;
    private PaymentProviderType paymentProviderType;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private double taxRate;
    private double totalPrice;
    private double totalPriceWithoutTax;
    private Currency currency;

    public abstract PaymentProviderType getPaymentProviderType();
}
