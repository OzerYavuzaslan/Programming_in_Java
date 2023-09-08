package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.AdyenPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.AdyenPaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class AdyenPaymentServiceImpl implements PaymentService<AdyenPaymentRequestDTO, AdyenPaymentResponseDTO> {
    @Override
    public AdyenPaymentResponseDTO pay(AdyenPaymentRequestDTO request) {
        //TODO: ADYEN SERVIS
        return new AdyenPaymentResponseDTO();
    }
}
