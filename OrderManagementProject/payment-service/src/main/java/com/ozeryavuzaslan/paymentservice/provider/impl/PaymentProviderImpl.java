package com.ozeryavuzaslan.paymentservice.provider.impl;

import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.provider.PaymentProvider;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import com.ozeryavuzaslan.paymentservice.service.impl.StripePaymentServiceImpl;
import com.ozeryavuzaslan.paymentservice.service.impl.CreditCardPaymentServiceImpl;
import com.ozeryavuzaslan.paymentservice.service.impl.PayAtHomePaymentServiceImpl;
import com.ozeryavuzaslan.paymentservice.service.impl.PaypalPaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProviderImpl implements PaymentProvider<PaymentService<PaymentRequestDTO, PaymentResponseDTO>> {
    private final ApplicationContext applicationContext;

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public PaymentService getProvider(PaymentProviderType paymentProviderType) {
        return switch (paymentProviderType) {
            case PAYPAL -> applicationContext.getBean(PaypalPaymentServiceImpl.class);
            case STRIPE -> applicationContext.getBean(StripePaymentServiceImpl.class);
            case CREDIT_CARD -> applicationContext.getBean(CreditCardPaymentServiceImpl.class);
            case PAY_AT_HOME -> applicationContext.getBean(PayAtHomePaymentServiceImpl.class);
        };
    }
}
