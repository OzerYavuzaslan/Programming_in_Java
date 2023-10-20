package com.ozeryavuzaslan.orderservice.controller;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.orderservice.service.OrderService;
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
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> takeOrder(@Valid @RequestBody OrderDTO orderDTO) throws Exception {
        return ResponseEntity.ok(orderService.takeOrder(orderDTO));
    }

/*
    @GetMapping("/getByOrderId/{orderid}")
    public ResponseEntity<OrderDTO> getSpecificOrder(@PathVariable long orderid){

    }
 */
}