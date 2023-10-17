package com.ozeryavuzaslan.basedomains.dto.stocks;

import com.ozeryavuzaslan.basedomains.customValidations.FutureDate;
import com.ozeryavuzaslan.basedomains.util.ValidateStock;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.ozeryavuzaslan.basedomains.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FutureDate(fieldName = "discountEndDate")
public class StockDTO implements Serializable, ValidateStock {
    private Long id;

    @NotNull(message = UUID_VALID_NULL_MSG)
    private UUID productCode;

    @NotNull(message = PRODUCT_NAME_NULL_MSG)
    @NotBlank(message = PRODUCT_NAME_BLANK_MSG)
    @NotEmpty(message = PRODUCT_NAME_EMPTY_MSG)
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = PRODUCT_NAME_SIZE_MSG)
    private String productName;

    @NotNull(message = BRAND_NAME_NOT_VALID)
    @NotBlank(message = BRAND_NAME_NOT_VALID)
    @NotEmpty(message = BRAND_NAME_NOT_VALID)
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = BRAND_NAME_NOT_VALID)
    private String brandName;

    @Email(message = EMAIL_NOT_VALID)
    @NotNull(message = EMAIL_NOT_VALID)
    @NotBlank(message = EMAIL_NOT_VALID)
    @NotEmpty(message = EMAIL_NOT_VALID)
    private String brandCompanyEmail;

    @Positive(message = QUANTITY_NEGATIVE_MSG)
    @NotNull(message = PRICE_OR_QUANTITY_NULL_MSG)
    private int quantity;

    @Positive(message = PRICE_NEGATIVE_MSG)
    @NotNull(message = PRICE_OR_QUANTITY_NULL_MSG)
    private double price;

    @PositiveOrZero(message = DISCOUNTS_NOT_VALID)
    private double discountAmount;

    @PositiveOrZero(message = DISCOUNTS_NOT_VALID)
    private double discountPercentage;

    @NotNull(message = CATEGORY_NULL_MSG)
    private CategoryDTO category;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime discountEndDate;
}
