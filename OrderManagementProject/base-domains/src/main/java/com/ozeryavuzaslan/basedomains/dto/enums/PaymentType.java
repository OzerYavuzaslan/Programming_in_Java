package com.ozeryavuzaslan.basedomains.dto.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
    PAYPAL(0),
    ADYEN(1),
    CREDIT_CARD(2),
    PAY_AT_HOME(3);

    private final int paymentValue;

    PaymentType(int paymentValue){
        this.paymentValue = paymentValue;
    }
}