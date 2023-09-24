package com.ozeryavuzaslan.basedomains.dto.payments;

import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RefundResponseForAsyncMsgDTO extends RefundResponseDTO implements Serializable {
    StripeRefundResponseDTO stripeRefundResponseDTO;
}
