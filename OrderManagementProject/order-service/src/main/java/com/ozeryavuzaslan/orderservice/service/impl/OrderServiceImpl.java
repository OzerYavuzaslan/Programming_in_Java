package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.orderservice.client.StockServiceClient;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final StockServiceClient stockServiceClient;

    @Override
    public OrderDTO getOrder(OrderDTO orderDTO) {


        return null;
    }
}