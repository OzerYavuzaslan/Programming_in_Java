package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;

public interface OrderService {
    OrderDTO takeOrder(OrderDTO orderDTO);
}
