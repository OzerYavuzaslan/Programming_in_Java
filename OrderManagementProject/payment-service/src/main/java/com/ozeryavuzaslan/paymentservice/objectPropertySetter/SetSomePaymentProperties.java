package com.ozeryavuzaslan.paymentservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentStatus;
import com.ozeryavuzaslan.paymentservice.dto.PaymentInvoiceDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;

import java.util.Map;

public interface SetSomePaymentProperties {
    StripePaymentResponseDTO setSomeProperties(Charge charge, StripePaymentRequestDTO stripePaymentRequestDTO);
    PaymentStatus getStatus(String status);
    StripeRefundResponseDTO setSomeProperties(Refund refund, StripeRefundRequestDTO stripeRefundRequestDTO);
    Map<String, Object> setSomeProperties(StripePaymentRequestDTO stripePaymentRequestDTO) throws StripeException;
    Map<String, Object> setSomeProperties(StripeRefundRequestDTO stripeRefundRequestDTO, PaymentInvoiceDTO paymentInvoiceDTO) throws StripeException;
}
