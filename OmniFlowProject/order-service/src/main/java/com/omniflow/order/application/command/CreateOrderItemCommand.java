package com.omniflow.order.application.command;

import java.math.BigDecimal;
import java.util.Objects;

import static com.omniflow.order.domain.constant.OrderErrorMessages.*;

/**
 * Application command representing a single basket line of an order creation request.
 *
 * @param productId   catalogue product identifier
 * @param productName product name snapshot at purchase time
 * @param quantity    ordered quantity
 * @param unitPrice   unit price amount
 * @param currency    ISO-4217 currency code
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record CreateOrderItemCommand(String productId, String productName, int quantity, BigDecimal unitPrice, String currency) {

    public CreateOrderItemCommand {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException(MSG_PRODUCT_ID_NULL);
        }

        if (Objects.isNull(productName)) {
            throw new IllegalArgumentException(MSG_PRODUCT_NAME_NULL);
        }

        if (Objects.isNull(unitPrice)) {
            throw new IllegalArgumentException(MSG_UNIT_PRICE_NULL);
        }

        if (Objects.isNull(currency)) {
            throw new IllegalArgumentException(MSG_CURRENCY_NULL);
        }
    }
}
