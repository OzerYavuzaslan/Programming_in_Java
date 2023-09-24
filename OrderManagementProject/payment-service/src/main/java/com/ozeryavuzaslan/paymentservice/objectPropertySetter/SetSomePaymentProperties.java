package com.ozeryavuzaslan.paymentservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentStatus;
import com.stripe.model.Charge;
import com.stripe.model.Refund;

public interface SetSomePaymentProperties {
    StripePaymentResponseDTO setSomeProperties(Charge charge, StripePaymentRequestDTO stripePaymentRequestDTO);
    PaymentStatus getStatus(String status);
    StripeRefundResponseDTO setSomeProperties(Refund refund, StripeRefundRequestDTO stripeRefundRequestDTO);
}
