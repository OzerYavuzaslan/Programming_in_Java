package com.ozeryavuzaslan.paymentservice.gateway.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.gateway.PaymentGateway;
import com.ozeryavuzaslan.paymentservice.provider.PaymentProvider;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway<PaymentResponseDTO, PaymentRequestDTO> {
    private final PaymentProvider<PaymentService<PaymentRequestDTO, PaymentResponseDTO>> paymentProvider;

    @Override
    public PaymentResponseDTO makePayment(PaymentRequestDTO paymentRequestDTO) {
        return paymentProvider.getProvider(paymentRequestDTO.getPaymentProviderType()).pay(paymentRequestDTO);
    }
}
