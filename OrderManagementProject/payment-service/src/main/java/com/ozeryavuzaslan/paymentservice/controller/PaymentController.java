package com.ozeryavuzaslan.paymentservice.controller;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;
import com.ozeryavuzaslan.paymentservice.paymentGateway.PaymentGateway;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentGateway<PaymentResponseDTO, PaymentRequestDTO, RefundResponseDTO, RefundRequestDTO> paymentGateway;

    @PostMapping("/stripe/pay")
    public ResponseEntity<PaymentResponseDTO> processPayment(@Valid @RequestBody StripePaymentRequestDTO stripePaymentRequestDTO) throws Exception {
        return ResponseEntity.ok(paymentGateway.makePayment(stripePaymentRequestDTO));
    }

    @PostMapping("/stripe/refund")
    public ResponseEntity<RefundResponseDTO> processRefund(@Valid @RequestBody StripeRefundRequestDTO stripeRefundRequestDTO) throws Exception {
        return ResponseEntity.ok(paymentGateway.makeRefund(stripeRefundRequestDTO));
    }
}