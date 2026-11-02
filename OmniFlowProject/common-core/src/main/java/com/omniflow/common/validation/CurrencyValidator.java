package com.omniflow.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.Currency;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasLength(value)) {
            return true;
        }

        try {
            Currency.getInstance(value.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }
}