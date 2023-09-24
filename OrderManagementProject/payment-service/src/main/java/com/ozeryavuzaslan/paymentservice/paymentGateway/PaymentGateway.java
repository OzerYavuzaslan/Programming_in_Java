package com.ozeryavuzaslan.paymentservice.paymentGateway;

import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;

public interface PaymentGateway<T extends PaymentResponseDTO, K extends PaymentRequestDTO, M extends RefundResponseDTO, N extends RefundRequestDTO> {
    T makePayment(K paymentRequestDTO) throws Exception;
    M makeRefund(N refundRequestDTO) throws Exception;
}
