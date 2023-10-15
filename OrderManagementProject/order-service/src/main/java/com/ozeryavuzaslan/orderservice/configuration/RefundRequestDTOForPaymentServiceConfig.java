package com.ozeryavuzaslan.orderservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.payments.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefundRequestDTOForPaymentServiceConfig {
    @Bean
    public RefundRequestDTOForPaymentService getRefundRequestDTOForPaymentService(){
        return createRefundRequestDTOForPaymentService(createStripeRefundRequestDTO());
    }

    private StripeRefundRequestDTO createStripeRefundRequestDTO() {
        return new StripeRefundRequestDTO();
    }

    private RefundRequestDTOForPaymentService createRefundRequestDTOForPaymentService(StripeRefundRequestDTO stripeRefundRequestDTO) {
        return new RefundRequestDTOForPaymentService(stripeRefundRequestDTO);
    }
}
