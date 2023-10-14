package com.ozeryavuzaslan.orderservice.client;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundRequestDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping("${base.endpoint}" + "${payment.base.endpoint}" + "${payment.stripe.base.endpoint}" + "${payment.pay.endpoint}")
    Response payViaStripe(@RequestBody StripePaymentRequestDTO stripePaymentRequestDTO);

    @PostMapping("${base.endpoint}" + "${payment.base.endpoint}" + "${payment.stripe.base.endpoint}" + "${payment.refund.endpoint}")
    Response refundViaStripe(@RequestBody StripeRefundRequestDTO stripeRefundRequestDTO);
}
