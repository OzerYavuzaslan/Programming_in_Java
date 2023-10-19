package com.ozeryavuzaslan.orderservice.dto;

import com.ozeryavuzaslan.basedomains.dto.payments.CreditCardPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaypalPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
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
