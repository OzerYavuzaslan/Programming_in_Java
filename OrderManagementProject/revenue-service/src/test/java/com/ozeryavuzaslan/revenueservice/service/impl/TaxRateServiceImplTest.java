package com.ozeryavuzaslan.revenueservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.revenueservice.exception.TaxRateNotFoundException;
import com.ozeryavuzaslan.revenueservice.service.TaxRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaxRateServiceImplTest {
    private final TaxRateService taxRateService;

    @Value("${tax.rate.not.found}")
    private String taxRateNotFoundMsg;

    @Autowired
    public TaxRateServiceImplTest(TaxRateService taxRateService) {
        this.taxRateService = taxRateService;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void with_correct_inputs_should_get_correct_tax_rate() {
        TaxRateDTO actualTaxRateDTO = taxRateService.getTaxRate(2023, 10, TaxRateType.KDV);
        TaxRateDTO expectedTaxRateDTO = new TaxRateDTO(261, 2023, 10, 20D, TaxRateType.KDV);
        assertEquals(expectedTaxRateDTO, actualTaxRateDTO);
    }

    @Test
    public void should_throw_tax_rate_not_found_exception_when_inputs_are_invalid() {
        Class<TaxRateNotFoundException> expectedExceptionType = TaxRateNotFoundException.class;

        TaxRateNotFoundException thrownException = assertThrows(
                expectedExceptionType, () -> taxRateService.getTaxRate(2024, 1, TaxRateType.KDV)
        );

        assertEquals(taxRateNotFoundMsg, thrownException.getMessage());
    }
}
