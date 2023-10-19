package com.ozeryavuzaslan.basedomains.dto.revenues;

import com.ozeryavuzaslan.basedomains.customValidations.ValidMonth;
import com.ozeryavuzaslan.basedomains.customValidations.ValidYear;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.ozeryavuzaslan.basedomains.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxRateDTO implements Serializable {
    private long id;

    @ValidYear(message = YEAR_NOT_VALID)
    private int year;

    @ValidMonth(message = MONTH_NOT_VALID)
    private int month;

    @NotNull(message = TAX_RATE_NOT_VALID)
    @Positive(message = TAX_RATE_NOT_VALID)
    private double rate;
    private TaxRateType taxRateType;
}
