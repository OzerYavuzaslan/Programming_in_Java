package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.PaypalPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaypalPaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaypalPaymentServiceImpl implements PaymentService<PaypalPaymentRequestDTO, PaypalPaymentResponseDTO> {
    @Override
    public PaypalPaymentResponseDTO pay(PaypalPaymentRequestDTO request) {
        //TODO:PAYPAL SERVISI

        return new PaypalPaymentResponseDTO();
    }
}
