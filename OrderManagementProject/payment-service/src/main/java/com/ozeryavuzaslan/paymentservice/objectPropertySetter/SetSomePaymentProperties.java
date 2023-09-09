package com.ozeryavuzaslan.paymentservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.stripe.model.Charge;

public interface SetSomePaymentProperties {
    StripePaymentResponseDTO setSomeProperties(Charge charge, StripePaymentRequestDTO stripePaymentRequestDTO);
}
