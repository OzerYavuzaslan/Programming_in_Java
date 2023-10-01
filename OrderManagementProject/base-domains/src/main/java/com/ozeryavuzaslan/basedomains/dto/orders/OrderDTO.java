package com.ozeryavuzaslan.basedomains.dto.orders;

import com.ozeryavuzaslan.basedomains.dto.payments.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.MonetaryUnitType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentProviderType;
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
    private PaymentProviderType paymentProviderType;;
    private CurrencyType currencyType;
    private MonetaryUnitType monetaryUnitType;
    private List<OrderStockDTO> orderStockList;
}