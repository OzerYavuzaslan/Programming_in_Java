package com.ozeryavuzaslan.basedomains.dto.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozeryavuzaslan.basedomains.dto.orders.enums.OrderStatusType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.MonetaryUnitType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentStatus;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static com.ozeryavuzaslan.basedomains.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {
    private long id;

    @Email(message = EMAIL_NOT_VALID)
    @NotNull(message = EMAIL_NOT_VALID)
    @NotEmpty(message = EMAIL_NOT_VALID)
    @NotBlank(message = EMAIL_NOT_VALID)
    private String email;

    @NotNull(message = NAME_SURNAME_NOT_VALID)
    @NotEmpty(message = NAME_SURNAME_NOT_VALID)
    @NotBlank(message = NAME_SURNAME_NOT_VALID)
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = PERSON_NAME_NOT_VALID)
    private String name;

    @NotNull(message = NAME_SURNAME_NOT_VALID)
    @NotEmpty(message = NAME_SURNAME_NOT_VALID)
    @NotBlank(message = NAME_SURNAME_NOT_VALID)
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = PERSON_NAME_NOT_VALID)
    private String surname;

    @NotNull(message = PHONE_NUMBER_NOT_VALID)
    @NotEmpty(message = PHONE_NUMBER_NOT_VALID)
    @NotBlank(message = PHONE_NUMBER_NOT_VALID)
    private String phoneNumber;

    @NotNull(message = ADDRESS_NOT_VALID)
    @NotEmpty(message = ADDRESS_NOT_VALID)
    @NotBlank(message = ADDRESS_NOT_VALID)
    @Size(max = ADDRESS_MAX_SIZE, message = ADDRESS_NOT_VALID)
    private String address1;

    @NotNull(message = ADDRESS_NOT_VALID)
    @NotEmpty(message = ADDRESS_NOT_VALID)
    @NotBlank(message = ADDRESS_NOT_VALID)
    @Size(max = ADDRESS_MAX_SIZE, message = ADDRESS_NOT_VALID)
    private String address2;

    private double taxRate;
    private OrderStatusType orderStatusType;
    private ReserveType reserveType;
    private PaymentStatus paymentStatus;
    private PaymentProviderType paymentProviderType;
    private CurrencyType currencyType;
    private MonetaryUnitType monetaryUnitType;
    private String paymentid;
    private double totalPrice;
    private double totalPriceWithoutTax;
    private double totalPriceWithDiscount;
    private double totalPriceWithDiscountWithoutTax;

    @NotNull(message = ORDER_STOCK_LIST_NOT_VALID)
    private List<OrderStockDTO> orderStockList;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}