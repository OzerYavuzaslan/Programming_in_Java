package com.ozeryavuzaslan.basedomains.customValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.ozeryavuzaslan.basedomains.util.Constants.MIN_YEAR;

public class YearConstraintValidator implements ConstraintValidator<ValidYear, Integer> {
    @Override
    public void initialize(ValidYear year) {}

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        return year != null && year > MIN_YEAR;
    }
}



