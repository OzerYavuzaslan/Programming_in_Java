package com.ozeryavuzaslan.paymentservice.paymentGateway.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;
import com.ozeryavuzaslan.paymentservice.paymentGateway.PaymentGateway;
import com.ozeryavuzaslan.paymentservice.provider.PaymentProvider;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway<PaymentResponseDTO, PaymentRequestDTO, RefundResponseDTO, RefundRequestDTO> {
    private final PaymentProvider<PaymentService<PaymentRequestDTO, PaymentResponseDTO, RefundRequestDTO, RefundResponseDTO>> paymentProvider;

    @Override
    public PaymentResponseDTO makePayment(PaymentRequestDTO paymentRequestDTO) throws StripeException, JsonProcessingException {
        return paymentProvider.getProvider(paymentRequestDTO.getPaymentProviderType()).pay(paymentRequestDTO);
    }

    @Override
    public RefundResponseDTO makeRefund(RefundRequestDTO refundRequestDTO) throws StripeException, JsonProcessingException {
        return paymentProvider.getProvider(refundRequestDTO.getPaymentProviderType()).refund(refundRequestDTO);
    }
}
