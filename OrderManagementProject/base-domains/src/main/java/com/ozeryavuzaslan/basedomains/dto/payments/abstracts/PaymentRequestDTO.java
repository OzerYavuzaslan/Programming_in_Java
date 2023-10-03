package com.ozeryavuzaslan.basedomains.dto.payments.abstracts;

import com.ozeryavuzaslan.basedomains.customValidations.EnumNamePattern;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.MonetaryUnitType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentProviderType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ozeryavuzaslan.basedomains.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PaymentRequestDTO {
    @NotNull(message = ORDER_ID_NOT_VALID)
    @Positive(message = ORDER_ID_NOT_VALID)
    private long orderid;

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

    @Email(message = EMAIL_NOT_VALID)
    @NotNull(message = EMAIL_NOT_VALID)
    @NotEmpty(message = EMAIL_NOT_VALID)
    @NotBlank(message = EMAIL_NOT_VALID)
    private String email;

    @NotNull(message = PAYMENT_AMOUNT_INFO_NOT_VALID)
    @Positive(message = PAYMENT_AMOUNT_INFO_NOT_VALID)
    private double taxRate;

    @NotNull(message = PAYMENT_AMOUNT_INFO_NOT_VALID)
    @Positive(message = PAYMENT_AMOUNT_INFO_NOT_VALID)
    private double totalPrice;

    @NotNull(message = PAYMENT_AMOUNT_INFO_NOT_VALID)
    @Positive(message = PAYMENT_AMOUNT_INFO_NOT_VALID)
    private double totalPriceWithoutTax;

    @EnumNamePattern(regexp = CURRENCY_VALIDATION_LIST, message = CURRENCY_NOT_VALID)
    private CurrencyType currencyType;

    @EnumNamePattern(regexp = MONETARY_UNIT_VALIDATION_LIST, message = MONETARY_UNIT_NOT_VALID)
    private MonetaryUnitType monetaryUnitType;

    @EnumNamePattern(regexp = PAYMENT_PROVIDER_TYPE_VALIDATION_LIST, message = PAYMENT_PROVIDER_NOT_VALID)
    private PaymentProviderType paymentProviderType;

    public abstract PaymentProviderType getPaymentProviderType();
}
