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
public class StockWithoutUUIDDTO implements Serializable {
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private UUID productCode;

    @NotNull(message = PRODUCT_NAME_NULL_MSG)
    @NotBlank(message = PRODUCT_NAME_BLANK_MSG)
    @NotEmpty(message = PRODUCT_NAME_EMPTY_MSG)
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = PRODUCT_NAME_SIZE_MSG)
    private String productName;

    @NotNull(message = PRICE_OR_QUANTITY_NULL_MSG)
    @NegativeOrZero(message = QUANTITY_NEGATIVE_MSG)
    private int quantity;

    @NotNull(message = PRICE_OR_QUANTITY_NULL_MSG)
    @NegativeOrZero(message = PRICE_NEGATIVE_MSG)
    private double price;

    @NotNull(message = CATEGORY_NULL_MSG)
    private CategoryWithoutUUIDDTO category;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}
