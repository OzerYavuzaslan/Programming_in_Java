package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.PaypalPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaypalPaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
//Refund concrete classlarını sonra yazarız
public class PaypalPaymentServiceImpl implements PaymentService<PaypalPaymentRequestDTO, PaypalPaymentResponseDTO, RefundRequestDTO, RefundResponseDTO> {
    @Override
    public PaypalPaymentResponseDTO pay(PaypalPaymentRequestDTO request) {
        //TODO:TODO: Örnek olması açısından var. StripePaymentServiceImpl classında ödeme yapılıyor.

        return new PaypalPaymentResponseDTO();
    }

    @Override
    public RefundResponseDTO refund(RefundRequestDTO refundRequest) throws StripeException {
        //TODO:TODO: Örnek olması açısından var. StripePaymentServiceImpl classında refund yapılıyor.
        return null;
    }
}
