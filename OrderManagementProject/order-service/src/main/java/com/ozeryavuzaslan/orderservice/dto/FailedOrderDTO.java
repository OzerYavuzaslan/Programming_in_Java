package com.ozeryavuzaslan.orderservice.dto;

import com.ozeryavuzaslan.orderservice.model.enums.PaymentRollbackState;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackPhase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FailedOrderDTO {
    private long id;
    private long orderid;
    private String paymentid;
    private boolean orderRollbackStatus;
    private PaymentRollbackState paymentRollbackState;
    private RollbackPhase rollbackPhase;
    private List<FailedOrderStockDTO> failedOrderStockList;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}
