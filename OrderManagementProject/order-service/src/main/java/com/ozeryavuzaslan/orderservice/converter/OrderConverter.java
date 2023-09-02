package com.ozeryavuzaslan.orderservice.converter;


import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderEventDTO;
import com.ozeryavuzaslan.basedomains.dto.enums.OrderStatusType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class OrderConverter {
    private OrderEventDTO orderEventDTO;

    public OrderEventDTO convert(OrderDTO orderDTO){
        orderDTO.setOrderID(UUID.randomUUID().toString());

        orderEventDTO.setOrderDTO(orderDTO);
        orderEventDTO.setMessage("Order status is pending state");
        orderEventDTO.setOrderStatusType(OrderStatusType.PENDING);

        return orderEventDTO;
    }
}