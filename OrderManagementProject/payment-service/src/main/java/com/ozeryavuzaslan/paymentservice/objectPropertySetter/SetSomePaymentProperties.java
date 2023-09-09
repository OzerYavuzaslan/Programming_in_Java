package com.ozeryavuzaslan.paymentservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.enums.PaymentStatus;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.stripe.model.Charge;

public interface SetSomePaymentProperties {
    StripePaymentResponseDTO setSomeProperties(Charge charge, StripePaymentRequestDTO stripePaymentRequestDTO);
    PaymentStatus getPaymentStatus(String status);
}
