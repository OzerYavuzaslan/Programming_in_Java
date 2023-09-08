package com.ozeryavuzaslan.paymentservice.provider;

import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;

public interface PaymentProvider<T> {
    T getProvider(PaymentProviderType paymentProviderType);
}
