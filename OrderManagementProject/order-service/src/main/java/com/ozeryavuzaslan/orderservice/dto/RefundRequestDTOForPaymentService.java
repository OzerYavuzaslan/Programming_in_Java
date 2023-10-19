package com.ozeryavuzaslan.orderservice.dto;

import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequestDTOForPaymentService {
    //Başka ödeme sistemlerinin refundları da eklenebilir.
    private StripeRefundRequestDTO stripeRefundRequestDTO;
}
