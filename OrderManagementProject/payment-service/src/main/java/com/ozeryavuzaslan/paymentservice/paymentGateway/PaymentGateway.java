package com.ozeryavuzaslan.paymentservice.paymentGateway;

import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;

public interface PaymentGateway<T extends PaymentResponseDTO, K extends PaymentRequestDTO> {
    T makePayment(K paymentRequest) throws Exception;
}
