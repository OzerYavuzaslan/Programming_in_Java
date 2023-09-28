package com.ozeryavuzaslan.basedomains.customValidations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.ozeryavuzaslan.basedomains.util.Constants.FUTURE_DATE_NOT_VALID;

@Documented
@Constraint(validatedBy = FutureDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {
    String message() default FUTURE_DATE_NOT_VALID;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String fieldName();
}