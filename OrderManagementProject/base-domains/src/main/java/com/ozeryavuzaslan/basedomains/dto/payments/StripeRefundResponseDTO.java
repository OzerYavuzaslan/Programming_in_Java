package com.ozeryavuzaslan.basedomains.dto.payments;

import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StripeRefundResponseDTO extends RefundResponseDTO {
    private String userid;
    private String balanceTransactionId;
    private String paymentid;
    private String refundid;
}
