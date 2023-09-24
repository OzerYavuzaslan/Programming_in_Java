package com.ozeryavuzaslan.orderservice.converter;


import com.ozeryavuzaslan.basedomains.dto.orders.enums.OrderStatusType;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderEventDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderConverter {
    private OrderEventDTO orderEventDTO;

    public OrderEventDTO convert(OrderDTO orderDTO){
     //   orderDTO.setOrderid(UUID.randomUUID());

        orderEventDTO.setOrderDTO(orderDTO);
        orderEventDTO.setMessage("Order status is pending state");
        orderEventDTO.setOrderStatusType(OrderStatusType.PENDING);

        return orderEventDTO;
    }
}