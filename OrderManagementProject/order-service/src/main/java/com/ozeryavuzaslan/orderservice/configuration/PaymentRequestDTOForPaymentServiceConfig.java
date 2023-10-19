package com.ozeryavuzaslan.orderservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.payments.CreditCardPaymentRequestDTO;
import com.ozeryavuzaslan.orderservice.dto.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.payments.PaypalPaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRequestDTOForPaymentServiceConfig {
    @Bean
    public PaypalPaymentRequestDTO getPaypalPaymentRequestDTO() {
        return new PaypalPaymentRequestDTO();
    }

    @Bean
    public StripePaymentRequestDTO getStripePaymentRequestDTO() {
        return new StripePaymentRequestDTO();
    }

    @Bean
    public CreditCardPaymentRequestDTO getCreditCardPaymentRequestDTO() {
        return new CreditCardPaymentRequestDTO();
    }

    @Bean
    public PaymentRequestDTOForPaymentService getPaymentRequestDTOForPaymentService(PaypalPaymentRequestDTO paypalPaymentRequestDTO,
                                                                                 StripePaymentRequestDTO stripePaymentRequestDTO,
                                                                                 CreditCardPaymentRequestDTO creditCardPaymentRequestDTO) {
        return new PaymentRequestDTOForPaymentService(paypalPaymentRequestDTO, stripePaymentRequestDTO, creditCardPaymentRequestDTO);
    }
}
