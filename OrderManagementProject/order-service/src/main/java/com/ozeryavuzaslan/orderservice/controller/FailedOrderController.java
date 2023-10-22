package com.ozeryavuzaslan.orderservice.controller;

import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;
import com.ozeryavuzaslan.orderservice.service.FailedOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class FailedOrderController {
    private final FailedOrderService failedOrderService;

    @GetMapping("/getSpecificFailedOrder/{failedOrderId}")
    public ResponseEntity<FailedOrderDTO> getSpecificFailedOrderById(@PathVariable long failedOrderId){
        return ResponseEntity.ok(failedOrderService.getSpecificFailedOrder(failedOrderId));
    }
}
