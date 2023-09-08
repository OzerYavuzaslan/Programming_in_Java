package com.ozeryavuzaslan.basedomains.dto.enums;

import lombok.Getter;

@Getter
public enum PaymentProviderType {
    PAYPAL,
    ADYEN,
    CREDIT_CARD,
    PAY_AT_HOME;

/*
    PAYPAL(0),
    ADYEN(1),
    CREDIT_CARD(2),
    PAY_AT_HOME(3);

    private final int paymentTypeValue;

    PaymentProviderType(int paymentTypeValue){
        this.paymentTypeValue = paymentTypeValue;
    }

*/
}