package com.ozeryavuzaslan.orderservice.controller;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    @PostMapping
    public ResponseEntity<OrderDTO> getOrder(@Valid @RequestBody OrderDTO orderDTO){
        return null;
    }
}