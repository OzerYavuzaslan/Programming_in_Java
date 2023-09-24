package com.ozeryavuzaslan.paymentservice.service;

import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;
import com.stripe.exception.StripeException;

public interface PaymentService<T extends PaymentRequestDTO, R extends PaymentResponseDTO, M extends RefundRequestDTO, N extends RefundResponseDTO> {
    R pay(T paymentRequest) throws StripeException;
    N refund(M refundRequest) throws StripeException;
}
