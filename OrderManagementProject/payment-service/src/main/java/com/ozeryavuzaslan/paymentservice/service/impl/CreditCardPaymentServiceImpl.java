package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.CreditCardPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.CreditCardPaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
//Refund concrete classlarını sonra yazarız
public class CreditCardPaymentServiceImpl implements PaymentService<CreditCardPaymentRequestDTO, CreditCardPaymentResponseDTO, RefundRequestDTO, RefundResponseDTO> {
    @Override
    public CreditCardPaymentResponseDTO pay(CreditCardPaymentRequestDTO request) {
        //TODO: Örnek olması açısından var. StripePaymentServiceImpl classında ödeme yapılıyor.
        return new CreditCardPaymentResponseDTO();
    }

    @Override
    public RefundResponseDTO refund(RefundRequestDTO refundRequest) throws StripeException {
        //TODO:TODO: Örnek olması açısından var. StripePaymentServiceImpl classında refund yapılıyor.
        return null;
    }
}
