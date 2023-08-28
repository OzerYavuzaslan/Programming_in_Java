package com.ozeryavuzaslan.orderservice.controller;

import com.ozeryavuzaslan.basedomains.dto.OrderDTO;
import com.ozeryavuzaslan.orderservice.kafka.OrderProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderProducer orderProducer;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public ResponseEntity<String> placeOrder(@Valid @RequestBody OrderDTO orderDTO){
        orderProducer.sendMessage(orderDTO);
        LOGGER.info(String.format("Order has been received. Method = placeOrder(@RequestBody OrderDTO orderDTO) --> %s", orderDTO));
        return ResponseEntity.ok("Order has been taken.");
    }
}
