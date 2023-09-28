package com.ozeryavuzaslan.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "STOCK-SERVICE")
public interface StockServiceClient {
}
