package com.ozeryavuzaslan.basedomains.dto.payments;


import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StripePaymentResponseDTO extends PaymentResponseDTO {
    private String userid;
    private String receiptUrl;
    private String balanceTransactionId;
    private String paymentid;
}
