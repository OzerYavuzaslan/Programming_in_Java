package com.ozeryavuzaslan.basedomains.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStockDTO implements Serializable {
    private long id;
    private int quantity;
    private UUID productCode;
    private String productName;
}