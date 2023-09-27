package com.ozeryavuzaslan.basedomains.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {
    private long id;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private List<OrderStockDTO> orderStockDTOList;
}