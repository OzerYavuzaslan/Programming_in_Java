package com.ozeryavuzaslan.paymentservice.service;

import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;

public interface PaymentFinderService<R extends PaymentResponseDTO, N extends RefundResponseDTO> {
    R getPayment(long orderid);
    N getRefund(long orderid);
}
