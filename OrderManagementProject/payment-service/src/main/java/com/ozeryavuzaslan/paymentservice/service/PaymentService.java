package com.ozeryavuzaslan.paymentservice.service;

import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;

public interface PaymentService<T extends PaymentRequestDTO, R extends PaymentResponseDTO> {
    R pay(T request) throws Exception;
}
