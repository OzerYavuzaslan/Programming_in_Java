package com.ozeryavuzaslan.emailservice.dto;

import com.ozeryavuzaslan.emailservice.dto.enums.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO {
    private String message;
    private OrderStatusType orderStatusType;
    private OrderDTO orderDTO;
}
