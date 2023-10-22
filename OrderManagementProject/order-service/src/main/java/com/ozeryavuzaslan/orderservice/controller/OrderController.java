package com.ozeryavuzaslan.orderservice.controller;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.util.CustomLocation;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
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
    public ResponseEntity<OrderDTO> getSpecificOrder(@PathVariable long orderid) {
        return ResponseEntity.ok(orderService.getByOrderID(orderid));
    }

    @PatchMapping("/prepareByOrderId/{orderid}")
    public ResponseEntity<OrderDTO> prepareByOrderID(@PathVariable long orderid) {
        return ResponseEntity.ok(orderService.prepareByOrderID(orderid));
    }

    @PatchMapping("/cancelByOrderId/{orderid}")
    public ResponseEntity<OrderDTO> cancelByOrderID(@PathVariable long orderid) throws Exception {
        return ResponseEntity.ok(orderService.cancelByOrderID(orderid));
    }

    @PatchMapping("/deliveredByOrderId/{orderid}")
    public ResponseEntity<OrderDTO> deliveredByOrderID(@PathVariable long orderid) {
        return ResponseEntity.ok(orderService.deliverByOrderID(orderid));
    }

    @PatchMapping("/giveToCargoByOrderId/{orderid}")
    public ResponseEntity<OrderDTO> giveToOrderCompanyByOrderID(@PathVariable long orderid) {
        return ResponseEntity.ok(orderService.giveToCargoCompanyByOrderID(orderid));
    }
}