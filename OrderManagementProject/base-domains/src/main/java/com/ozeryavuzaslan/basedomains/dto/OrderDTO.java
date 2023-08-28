package com.ozeryavuzaslan.basedomains.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String orderID;

    @NotNull(message = "NotNull!!!")
    @NotBlank(message = "NotBlank!!!")
    @NotEmpty(message = "NotEmpty!!!")
    @Size(min = 4,  max = 11, message = "SIZE!!!!")
    private String productName;

    private int quantity;
    private double price;
    private String ownerEmail;
    private String ownerFullName;
}
