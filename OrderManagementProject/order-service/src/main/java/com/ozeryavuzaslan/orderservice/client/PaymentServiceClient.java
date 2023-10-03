package com.ozeryavuzaslan.orderservice.client;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentServiceClient {
    @GetMapping("${base.endpoint}" + "${payment.base.endpoint}" + "${payment.stripe.base.endpoint}" + "${payment.pay.endpoint}")
    PaymentResponseDTO payViaStripe(@RequestBody StripePaymentRequestDTO stripePaymentRequestDTO);

    @GetMapping("${base.endpoint}" + "${payment.base.endpoint}" + "${payment.stripe.base.endpoint}" + "${payment.refund.endpoint}")
    RefundResponseDTO refundViaStripe(@RequestBody StripeRefundRequestDTO stripeRefundRequestDTO);
}
