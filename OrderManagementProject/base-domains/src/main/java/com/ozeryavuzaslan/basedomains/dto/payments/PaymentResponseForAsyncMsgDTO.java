package com.ozeryavuzaslan.basedomains.dto.payments;

import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentResponseForAsyncMsgDTO extends PaymentResponseDTO implements Serializable {
    PaypalPaymentResponseDTO paypalPaymentResponseDTO;
    StripePaymentResponseDTO stripePaymentResponseDTO;
    CreditCardPaymentResponseDTO creditCardPaymentResponseDTO;
}
