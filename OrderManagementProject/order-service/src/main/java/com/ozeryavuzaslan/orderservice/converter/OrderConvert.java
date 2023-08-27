package com.ozeryavuzaslan.orderservice.converter;

import com.ozeryavuzaslan.orderservice.dto.OrderDTO;
import com.ozeryavuzaslan.orderservice.dto.OrderEventDTO;
import com.ozeryavuzaslan.orderservice.dto.enums.OrderStatusType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderConvert {
    public OrderEventDTO convert(OrderDTO orderDTO){
        orderDTO.setOrderID(UUID.randomUUID().toString());

        OrderEventDTO orderEventDTO = new OrderEventDTO();
        orderEventDTO.setOrderDTO(orderDTO);
        orderEventDTO.setMessage("Order status is pending state");
        orderEventDTO.setOrderStatusType(OrderStatusType.PENDING);

        return orderEventDTO;
    }
}
