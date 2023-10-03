package com.ozeryavuzaslan.orderservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.payments.CreditCardPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.payments.PaypalPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRequestDTOForPaymentServiceConfig {
    @Bean
    public PaypalPaymentRequestDTO paypalPaymentRequestDTO() {
        return new PaypalPaymentRequestDTO();
    }

    @Bean
    public StripePaymentRequestDTO stripePaymentRequestDTO() {
        return new StripePaymentRequestDTO();
    }

    @Bean
    public CreditCardPaymentRequestDTO creditCardPaymentRequestDTO() {
        return new CreditCardPaymentRequestDTO();
    }

    @Bean
    public PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService(PaypalPaymentRequestDTO paypalPaymentRequestDTO,
                                                                                 StripePaymentRequestDTO stripePaymentRequestDTO,
                                                                                 CreditCardPaymentRequestDTO creditCardPaymentRequestDTO) {
        return new PaymentRequestDTOForPaymentService(paypalPaymentRequestDTO, stripePaymentRequestDTO, creditCardPaymentRequestDTO);
    }
}
