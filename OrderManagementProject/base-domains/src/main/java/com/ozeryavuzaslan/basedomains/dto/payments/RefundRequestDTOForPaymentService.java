package com.ozeryavuzaslan.basedomains.dto.payments;

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
