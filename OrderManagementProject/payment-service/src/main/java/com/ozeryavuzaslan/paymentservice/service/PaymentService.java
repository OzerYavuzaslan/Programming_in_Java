package com.ozeryavuzaslan.paymentservice.service;

import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentResponseDTO;

public interface PaymentService <T extends PaymentRequestDTO, R extends PaymentResponseDTO> {
    R pay(T request);
}