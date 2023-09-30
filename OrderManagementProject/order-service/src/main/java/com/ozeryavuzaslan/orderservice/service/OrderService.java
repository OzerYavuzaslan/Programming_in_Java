package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDTO getOrder(Pageable pageable, OrderDTO orderDTO);
}
