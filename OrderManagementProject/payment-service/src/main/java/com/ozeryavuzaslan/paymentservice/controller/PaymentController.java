package com.ozeryavuzaslan.paymentservice.controller;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.gateway.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentGateway<PaymentResponseDTO, PaymentRequestDTO> paymentGateway;

    @PostMapping("/stripe/pay")
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody StripePaymentRequestDTO stripePaymentRequestDTO) throws Exception {
        return ResponseEntity.ok(paymentGateway.makePayment(stripePaymentRequestDTO));
    }
}