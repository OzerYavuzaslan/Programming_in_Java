package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentServiceImpl implements PaymentService<StripePaymentRequestDTO, StripePaymentResponseDTO> {
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.status}")
    private String stripePaymentStatus;

    @PostConstruct
    private void init(){
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    public StripePaymentResponseDTO pay(StripePaymentRequestDTO stripePaymentRequestDTO) throws Exception{
        Charge charge = stripePayment(stripePaymentRequestDTO);

        System.err.println(charge.toString());

        if (charge.getStatus().equalsIgnoreCase(stripePaymentStatus)){

        }

        return new StripePaymentResponseDTO();
    }

    private Charge stripePayment(StripePaymentRequestDTO stripePaymentRequestDTO) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();

        chargeParams.put("amount", (int) stripePaymentRequestDTO.getTotalPrice());
        chargeParams.put("currency", stripePaymentRequestDTO.getCurrency());

        Customer customer = Customer.retrieve(stripePaymentRequestDTO.getUserID());

        chargeParams.put("customer", customer.getId());
        return Charge.create(chargeParams);
    }
}
