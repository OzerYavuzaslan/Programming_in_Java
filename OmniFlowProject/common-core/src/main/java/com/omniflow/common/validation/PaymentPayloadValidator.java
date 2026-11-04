package com.omniflow.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class PaymentPayloadValidator implements ConstraintValidator<ValidPaymentPayload, PaymentPayloadAware> {
    private static final String PROPERTY_IBAN = "iban";
    private static final String CREDIT_CARD = "CREDIT_CARD";
    private static final String PROPERTY_CARD_NUMBER = "cardNumber";
    private static final String SEPA_DIRECT_DEBIT = "SEPA_DIRECT_DEBIT";
    private static final String PROPERTY_PAYMENT_METHOD = "paymentMethod";
    private static final String MSG_UNSUPPORTED_METHOD_PREFIX = "Unsupported payment method: ";
    private static final String MSG_IBAN_MANDATORY = "IBAN is mandatory for SEPA_DIRECT_DEBIT payments";
    private static final String MSG_CARD_MANDATORY = "Card number and CVV are mandatory for CREDIT_CARD payments";

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
                        .buildConstraintViolationWithTemplate(MSG_CARD_MANDATORY)
                        .addPropertyNode(PROPERTY_CARD_NUMBER)
                        .addConstraintViolation();

                return false;
            }
        } else if (tmpMethod.equalsIgnoreCase(SEPA_DIRECT_DEBIT)) {
            if (!StringUtils.hasText(paymentPayloadAware.getIban())) {
                constraintValidatorContext.disableDefaultConstraintViolation();

                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(MSG_IBAN_MANDATORY)
                        .addPropertyNode(PROPERTY_IBAN)
                        .addConstraintViolation();

                return false;
            }
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(MSG_UNSUPPORTED_METHOD_PREFIX + tmpMethod)
                    .addPropertyNode(PROPERTY_PAYMENT_METHOD)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}