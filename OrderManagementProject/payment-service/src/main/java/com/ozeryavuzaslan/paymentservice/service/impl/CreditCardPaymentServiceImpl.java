package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.CreditCardPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.CreditCardPaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class CreditCardPaymentServiceImpl implements PaymentService<CreditCardPaymentRequestDTO, CreditCardPaymentResponseDTO> {
    @Override
    public CreditCardPaymentResponseDTO pay(CreditCardPaymentRequestDTO request) {
        //TODO: Örnek olması açısından var. StripePaymentServiceImpl classında ödeme yapılıyor.
        return new CreditCardPaymentResponseDTO();
    }
}
