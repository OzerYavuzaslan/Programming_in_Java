package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderDTO getOrder(OrderDTO orderDTO) {



        return null;
    }
}