package com.ozeryavuzaslan.basedomains.dto.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTOForPaymentService {
    private PaypalPaymentRequestDTO paypalPaymentRequestDTO;
    private StripePaymentRequestDTO stripePaymentRequestDTO;
    private CreditCardPaymentRequestDTO creditCardPaymentRequestDTO;
}
