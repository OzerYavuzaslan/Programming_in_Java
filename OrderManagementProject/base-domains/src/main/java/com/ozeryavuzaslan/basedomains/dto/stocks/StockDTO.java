package com.ozeryavuzaslan.basedomains.dto.stocks;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class StockDTO implements Serializable {
    @JsonIgnore
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

    @NotNull(message = PRICE_OR_QUANTITY_NULL_MSG)
    @Positive(message = QUANTITY_NEGATIVE_MSG)
    private int quantity;

    @NotNull(message = PRICE_OR_QUANTITY_NULL_MSG)
    @Positive(message = PRICE_NEGATIVE_MSG)
    private double price;

    @NotNull(message = CATEGORY_NULL_MSG)
    private CategoryDTO category;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}
