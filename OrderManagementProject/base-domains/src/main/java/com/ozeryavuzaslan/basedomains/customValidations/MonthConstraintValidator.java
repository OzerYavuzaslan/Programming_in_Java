package com.ozeryavuzaslan.basedomains.customValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MonthConstraintValidator implements ConstraintValidator<ValidMonth, Integer> {
    @Override
    public void initialize(ValidMonth month) {}

    @Override
    public boolean isValid(Integer month, ConstraintValidatorContext context) {
        return month != null && month > 0 && month < 13;
    }
}



