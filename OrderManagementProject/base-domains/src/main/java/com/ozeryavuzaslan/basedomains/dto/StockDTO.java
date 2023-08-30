package com.ozeryavuzaslan.basedomains.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    @JsonIgnore
    private Long id;
    private String productName;
    private int count;
    private double price;
    private CategoryDTO category;
}
