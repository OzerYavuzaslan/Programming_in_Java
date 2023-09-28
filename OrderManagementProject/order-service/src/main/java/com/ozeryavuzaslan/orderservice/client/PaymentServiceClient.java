package com.ozeryavuzaslan.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentServiceClient {
}
