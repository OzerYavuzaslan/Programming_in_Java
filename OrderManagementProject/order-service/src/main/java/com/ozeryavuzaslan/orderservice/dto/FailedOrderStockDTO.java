package com.ozeryavuzaslan.orderservice.dto;

import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FailedOrderStockDTO {
    private long id;
    private long stockid;
    private long reserveStockID;
    private long failedOrderID;
    private ReserveType reserveType;
    private boolean reserveRollbackStatus;
    private boolean stockRollbackStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reserveDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}
