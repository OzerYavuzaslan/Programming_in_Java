package com.ozeryavuzaslan.orderservice.client;

import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "revenue-service")
public interface RevenueServiceClient {
    @GetMapping("${base.endpoint}" + "${revenue.base.endpoint}" + "${revenue.tax.rate.base.endpoint}" + "${revenue.get.specific.tax.rate.endpoint}")
    Response getSpecificTaxRate(@RequestParam int taxYear,
                                @RequestParam int taxMonth,
                                @RequestParam TaxRateType taxRateType);
}
