package com.ozeryavuzaslan.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String orderID;
    private String productName;
    private int quantity;
    private double price;
    private String ownerEmail;
    private String ownerFullName;
}
