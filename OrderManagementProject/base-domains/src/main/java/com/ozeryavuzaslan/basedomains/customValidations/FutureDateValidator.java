package com.ozeryavuzaslan.basedomains.customValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

import static com.ozeryavuzaslan.basedomains.util.Constants.FUTURE_DATE_NOT_VALID;
import static com.ozeryavuzaslan.basedomains.util.Constants.FUTURE_DATE_VALIDATION_REQUEST;

public class FutureDateValidator implements ConstraintValidator<FutureDate, ValidateStock> {
    @Override
    public void initialize(FutureDate constraintAnnotation) {
        String fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(ValidateStock validateStock, ConstraintValidatorContext constraintValidatorContext) {
        if ((validateStock.getDiscountAmount() > 0 || validateStock.getDiscountPercentage() > 0) && validateStock.getDiscountEndDate() == null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(FUTURE_DATE_VALIDATION_REQUEST).addPropertyNode("discountEndDate").addConstraintViolation();
            return false;
        }

        if (validateStock.getDiscountEndDate() != null && !validateStock.getDiscountEndDate().isAfter(LocalDateTime.now())
                && (validateStock.getDiscountAmount() > 0 || validateStock.getDiscountPercentage() > 0)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(FUTURE_DATE_NOT_VALID).addPropertyNode("discountEndDate").addConstraintViolation();
            return false;
        }

        return true;
    }
}