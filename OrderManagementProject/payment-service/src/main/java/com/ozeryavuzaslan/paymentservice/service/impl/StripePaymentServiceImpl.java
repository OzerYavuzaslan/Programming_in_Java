package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentServiceImpl implements PaymentService<StripePaymentRequestDTO, StripePaymentResponseDTO> {
    @Override
    public StripePaymentResponseDTO pay(StripePaymentRequestDTO request) {
        //TODO: Stripe SERVIS
        return new StripePaymentResponseDTO();
    }
}
