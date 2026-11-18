package com.omniflow.common.constant;

/**
 * Immutable utility class holding centralized error and validation messages
 * shared across common-core components.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public final class CommonErrorMessages {

    private CommonErrorMessages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final String MSG_UNEXPECTED_RESULT_STATE = "Unexpected state";
    public static final String MSG_UNSUPPORTED_METHOD_PREFIX = "Unsupported payment method: ";
    public static final String MSG_IBAN_MANDATORY = "IBAN is mandatory for SEPA_DIRECT_DEBIT payments";
    public static final String MSG_CARD_MANDATORY = "Card number and CVV are mandatory for CREDIT_CARD payments";
}
