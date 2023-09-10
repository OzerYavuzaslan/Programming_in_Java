package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.model.PaymentInvoice;
import com.ozeryavuzaslan.paymentservice.objectPropertySetter.SetSomePaymentProperties;
import com.ozeryavuzaslan.paymentservice.repository.PaymentRepository;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripePaymentServiceImpl implements PaymentService<StripePaymentRequestDTO, StripePaymentResponseDTO> {
    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;
    private final SetSomePaymentProperties setSomePaymentProperties;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    private void init(){
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    public StripePaymentResponseDTO pay(StripePaymentRequestDTO stripePaymentRequestDTO) throws Exception{
        Charge charge = stripePayment(stripePaymentRequestDTO);

        StripePaymentResponseDTO stripePaymentResponseDTO = setSomePaymentProperties.setSomeProperties(charge, stripePaymentRequestDTO);
        modelMapper.map(paymentRepository.save(modelMapper.map(stripePaymentResponseDTO, PaymentInvoice.class)), stripePaymentResponseDTO);
        return stripePaymentResponseDTO;
    }

    private Charge stripePayment(StripePaymentRequestDTO stripePaymentRequestDTO) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();

        chargeParams.put("amount", (int) stripePaymentRequestDTO.getTotalPrice());
        chargeParams.put("currency", stripePaymentRequestDTO.getCurrencyType());

        Customer customer = Customer.retrieve(stripePaymentRequestDTO.getUserid());

        chargeParams.put("customer", customer.getId());
        return Charge.create(chargeParams);
    }
}