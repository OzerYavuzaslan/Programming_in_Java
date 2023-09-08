package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.PayAtHomePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PayAtHomePaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PayAtHomePaymentServiceImpl implements PaymentService<PayAtHomePaymentRequestDTO, PayAtHomePaymentResponseDTO> {
    @Override
    public PayAtHomePaymentResponseDTO pay(PayAtHomePaymentRequestDTO payAtHomePaymentRequestDTO) {
        //TODO:kapıda ödeme

        return new PayAtHomePaymentResponseDTO();
    }
}
