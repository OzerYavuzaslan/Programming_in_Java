package com.ozeryavuzaslan.basedomains.dto.orders;

import com.ozeryavuzaslan.basedomains.dto.orders.enums.TaxRateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxRateDTO {
    private Long id;
    private int year;
    private int month;
    private double rate;
    private TaxRateType taxRateType;
}