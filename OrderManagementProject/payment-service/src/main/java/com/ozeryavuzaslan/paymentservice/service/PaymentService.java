package com.ozeryavuzaslan.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.stripe.exception.StripeException;

public interface PaymentService<T extends PaymentRequestDTO, R extends PaymentResponseDTO> {
    R pay(T request) throws StripeException, JsonProcessingException;
}
