package com.ozeryavuzaslan.paymentservice.provider;

import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentProviderType;

public interface PaymentProvider<T> {
    T getProvider(PaymentProviderType paymentProviderType);
}
