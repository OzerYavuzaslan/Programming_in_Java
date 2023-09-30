package com.ozeryavuzaslan.orderservice.client;

import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "REVENUE-SERVICE")
public interface RevenueServiceClient {
    @GetMapping("${base.endpoint}" + "${revenue.base.endpoint}" + "${revenue.tax.rate.base.endpoint}" + "${revenue.get.specific.tax.rate.endpoint}")
    public TaxRateDTO getSpecificTaxRate(@RequestParam int taxYear,
                                         @RequestParam int taxMonth,
                                         @RequestParam TaxRateType taxRateType);
}
