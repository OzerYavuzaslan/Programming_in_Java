package com.ozeryavuzaslan.orderservice.controller;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.util.CustomLocation;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @Value("${order.get.by.id.endpoint}")
    private String orderGetByIDEndpoint;

    @PostMapping
    public ResponseEntity<OrderDTO> takeOrder(@Valid @RequestBody OrderDTO orderDTO) throws Exception {
        return ResponseEntity
                .created(CustomLocation
                        .getURILocation(orderGetByIDEndpoint, orderService.takeOrder(orderDTO).getId()))
                .build();
    }

    @GetMapping("/getByOrderId/{orderid}")
    public ResponseEntity<OrderDTO> getSpecificOrder(@PathVariable long orderid){
        return ResponseEntity.ok(orderService.getByOrderID(orderid));
    }
}