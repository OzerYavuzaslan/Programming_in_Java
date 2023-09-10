package com.ozeryavuzaslan.paymentservice.controller;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.paymentGateway.PaymentGateway;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentGateway<PaymentResponseDTO, PaymentRequestDTO> paymentGateway;

    @PostMapping("/stripe/pay")
    public ResponseEntity<PaymentResponseDTO> processPayment(@Valid @RequestBody StripePaymentRequestDTO stripePaymentRequestDTO) throws Exception {
        return ResponseEntity.ok(paymentGateway.makePayment(stripePaymentRequestDTO));
    }
}