package com.omniflow.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class PaymentPayloadValidator implements ConstraintValidator<ValidPaymentPayload, PaymentPayloadAware> {
    private static final String CREDIT_CARD = "CREDIT_CARD";
    private static final String SEPA_DIRECT_DEBIT = "SEPA_DIRECT_DEBIT";

    @Override
    public boolean isValid(PaymentPayloadAware paymentPayloadAware, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(paymentPayloadAware)) {
            return true;
        }

        String tmpMethod = paymentPayloadAware.getPaymentMethod();

        if (!StringUtils.hasText(tmpMethod)) {
            return true;
        }

        if (tmpMethod.equalsIgnoreCase(CREDIT_CARD)) {
            if (!StringUtils.hasText(paymentPayloadAware.getCardNumber()) || !StringUtils.hasText(paymentPayloadAware.getCvv())) {
                constraintValidatorContext.disableDefaultConstraintViolation();

                constraintValidatorContext
                        .buildConstraintViolationWithTemplate("Card number and CVV are mandatory for CREDIT_CARD payments")
                        .addPropertyNode("cardNumber")
                        .addConstraintViolation();

                return false;
            }
        } else if (tmpMethod.equalsIgnoreCase(SEPA_DIRECT_DEBIT)) {
            if (!StringUtils.hasText(paymentPayloadAware.getIban())) {
                constraintValidatorContext.disableDefaultConstraintViolation();

                constraintValidatorContext
                        .buildConstraintViolationWithTemplate("IBAN is mandatory for SEPA_DIRECT_DEBIT payments")
                        .addPropertyNode("iban")
                        .addConstraintViolation();

                return false;
            }
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Unsupported payment method: " + tmpMethod)
                    .addPropertyNode("paymentMethod")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}