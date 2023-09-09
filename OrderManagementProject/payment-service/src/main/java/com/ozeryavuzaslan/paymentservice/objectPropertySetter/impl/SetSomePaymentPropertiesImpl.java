package com.ozeryavuzaslan.paymentservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.objectPropertySetter.SetSomePaymentProperties;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetSomePaymentPropertiesImpl implements SetSomePaymentProperties {
    private final ModelMapper modelMapper;

    @Override
    public StripePaymentResponseDTO setSomeProperties(Charge charge, StripePaymentRequestDTO stripePaymentRequestDTO) {
        StripePaymentResponseDTO stripePaymentResponseDTO = new StripePaymentResponseDTO();

        stripePaymentResponseDTO = modelMapper.map(stripePaymentRequestDTO, StripePaymentResponseDTO.class);
        stripePaymentResponseDTO.setReceiptUrl(charge.getReceiptUrl());
        stripePaymentResponseDTO.setBalanceTransactionId(charge.getBalanceTransaction());
        stripePaymentResponseDTO.setPaymentid(charge.getId());
        stripePaymentResponseDTO.setPaymentStatus(charge.getStatus());

        return stripePaymentResponseDTO;
    }
}
