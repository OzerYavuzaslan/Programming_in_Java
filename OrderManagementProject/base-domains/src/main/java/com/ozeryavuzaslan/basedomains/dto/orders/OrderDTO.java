package com.ozeryavuzaslan.basedomains.dto.orders;

import com.ozeryavuzaslan.basedomains.dto.orders.enums.OrderStatusType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.MonetaryUnitType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentStatus;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {
    private long id;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private String address1;
    private String address2;
    private double taxRate;
    private double totalPrice;
    private double totalPriceWithoutTax;
    private double totalPriceWithDiscount;
    private double totalPriceWithDiscountWithoutTax;
    private String paymentid;
    private OrderStatusType orderStatusType;
    private ReserveType reserveType;
    private PaymentStatus paymentStatus;
    private PaymentProviderType paymentProviderType;
    private CurrencyType currencyType;
    private MonetaryUnitType monetaryUnitType;
    private List<OrderStockDTO> orderStockList;
}