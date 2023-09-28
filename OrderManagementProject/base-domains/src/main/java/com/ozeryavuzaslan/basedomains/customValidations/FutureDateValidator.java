package com.ozeryavuzaslan.basedomains.customValidations;

import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

import static com.ozeryavuzaslan.basedomains.util.Constants.*;

public class FutureDateValidator implements ConstraintValidator<FutureDate, StockDTO> {
    @Override
    public void initialize(FutureDate constraintAnnotation) {
        String fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(StockDTO stockDTO, ConstraintValidatorContext context) {
        if ((stockDTO.getDiscountAmount() > 0 || stockDTO.getDiscountPercentage() > 0) && stockDTO.getDiscountEndDate() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(FUTURE_DATE_VALIDATION_REQUEST).addPropertyNode("discountEndDate").addConstraintViolation();
            return false;
        }

        if (stockDTO.getDiscountEndDate() != null && !stockDTO.getDiscountEndDate().isAfter(LocalDateTime.now())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(FUTURE_DATE_NOT_VALID).addPropertyNode("discountEndDate").addConstraintViolation();
            return false;
        }

        return true;
    }
}