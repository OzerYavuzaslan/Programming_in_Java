package com.ozeryavuzaslan.basedomains.dto.orders;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

import static com.ozeryavuzaslan.basedomains.util.Constants.*;
import static com.ozeryavuzaslan.basedomains.util.Constants.PRODUCT_NAME_EMPTY_MSG;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStockDTO implements Serializable {
    private long id;

    @NotNull(message = QUANTITY_NOT_VALID)
    @Positive(message = QUANTITY_NOT_VALID)
    private int quantity;

    @NotNull(message = UUID_VALID_NULL_MSG)
    private UUID productCode;

    @NotNull(message = PRODUCT_NAME_NULL_MSG)
    @NotBlank(message = PRODUCT_NAME_BLANK_MSG)
    @NotEmpty(message = PRODUCT_NAME_EMPTY_MSG)
    private String productName;
}