package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import com.ozeryavuzaslan.orderservice.service.PriceCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PriceCalculationServiceImpl implements PriceCalculationService {
    private final OrderPropertySetter orderPropertySetter;

    @Override
    public OrderDTO calculateOrderPrice(List<ReservedStockDTO> reservedStockDTOList, TaxRateDTO taxRateDTO, OrderDTO orderDTO) {
        double tmpTotalPrice = 0.0D;
        double tmpTotalPriceWithoutTax = 0.0D;
        double tmpTaxRate = taxRateDTO.getRate();

        for (ReservedStockDTO reservedStockDTO : reservedStockDTOList){
            tmpTotalPriceWithoutTax += reservedStockDTO.getStock().getPrice() * reservedStockDTO.getQuantity();
            tmpTotalPrice += tmpTotalPriceWithoutTax + reservedStockDTO.getStock().getPrice() * reservedStockDTO.getQuantity() * (tmpTaxRate / 100);
        }

        return orderPropertySetter.setSomeProperties(orderDTO, tmpTotalPrice, tmpTotalPriceWithoutTax, tmpTaxRate);
    }
}
