package com.ozeryavuzaslan.basedomains.dto.stocks;

import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.ozeryavuzaslan.basedomains.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservedStockDTO {
    private long id;

    private long orderid;

    @NotNull(message = UUID_VALID_NULL_MSG)
    private UUID productCode;

    @Positive(message = QUANTITY_NEGATIVE_MSG)
    @NotNull(message = PRICE_OR_QUANTITY_NULL_MSG)
    private int quantity;

    private ReserveType reserveType;
    private StockDTO stock;
}
