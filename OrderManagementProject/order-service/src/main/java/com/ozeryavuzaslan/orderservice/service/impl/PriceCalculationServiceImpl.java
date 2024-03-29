package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import com.ozeryavuzaslan.orderservice.service.PriceCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PriceCalculationServiceImpl implements PriceCalculationService {
    private final OrderPropertySetter orderPropertySetter;

    @Override
    public void calculateOrderPrice(List<ReservedStockDTO> reservedStockDTOList, TaxRateDTO taxRateDTO, OrderDTO orderDTO) {
        double tmpTotalPriceWithoutTax = 0.0;
        double tmpTotalPriceWithDiscountWithoutTax = 0.0;
        double tmpTotalPriceWithDiscount = 0.0;
        double tmpTotalPrice = 0.0;
        double taxRate = taxRateDTO.getRate();

        for (ReservedStockDTO reservedStock : reservedStockDTOList) {
            double unitPrice = reservedStock.getStock().getPrice();
            int quantity = reservedStock.getQuantity();

            double priceForTotalQuantity = unitPrice * quantity;
            tmpTotalPriceWithoutTax += priceForTotalQuantity;

            double taxForProductWithoutDiscount = priceForTotalQuantity * (taxRate / 100);
            tmpTotalPrice += priceForTotalQuantity + taxForProductWithoutDiscount;

            if (((!Objects.isNull(reservedStock.getStock().getDiscountEndDate()) )
                    && (LocalDateTime.now().isBefore(reservedStock.getStock().getDiscountEndDate())
                    || LocalDateTime.now().isEqual(reservedStock.getStock().getDiscountEndDate())))
                    && reservedStock.getStock().getDiscountPercentage() > 0) {
                double discountAmount = reservedStock.getStock().getDiscountAmount();
                double discountedPriceForProduct = priceForTotalQuantity - (discountAmount * quantity);
                tmpTotalPriceWithDiscountWithoutTax += discountedPriceForProduct;
                double taxForProduct = discountedPriceForProduct * (taxRate / 100);
                tmpTotalPriceWithDiscount += discountedPriceForProduct + taxForProduct;
            } else {
                tmpTotalPriceWithDiscountWithoutTax += priceForTotalQuantity;
                tmpTotalPriceWithDiscount += priceForTotalQuantity + taxForProductWithoutDiscount;
            }
        }

        orderPropertySetter.setSomeProperties(orderDTO, tmpTotalPriceWithoutTax, tmpTotalPriceWithDiscountWithoutTax,
                tmpTotalPrice, tmpTotalPriceWithDiscount, taxRate);
    }
}
