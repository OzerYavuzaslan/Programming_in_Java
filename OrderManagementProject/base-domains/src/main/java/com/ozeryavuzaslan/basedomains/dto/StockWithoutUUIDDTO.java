package com.ozeryavuzaslan.basedomains.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockWithoutUUIDDTO {
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private UUID productCode;
    private String productName;
    private int quantity;
    private double price;
    private CategoryDTO category;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}
