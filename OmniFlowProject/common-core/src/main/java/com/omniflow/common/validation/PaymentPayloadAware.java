package com.omniflow.common.validation;

public interface PaymentPayloadAware {
    String getPaymentMethod();
    String getCardNumber();
    String getCvv();
    String getIban();
}
