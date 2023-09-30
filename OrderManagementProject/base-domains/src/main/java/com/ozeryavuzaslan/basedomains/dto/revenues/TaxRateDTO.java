package com.ozeryavuzaslan.basedomains.dto.revenues;

import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
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
