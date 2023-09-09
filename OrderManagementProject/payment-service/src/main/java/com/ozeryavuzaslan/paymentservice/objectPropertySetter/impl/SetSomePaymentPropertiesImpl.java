package com.ozeryavuzaslan.paymentservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.enums.PaymentStatus;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.paymentservice.objectPropertySetter.SetSomePaymentProperties;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetSomePaymentPropertiesImpl implements SetSomePaymentProperties {
    private final ModelMapper modelMapper;

    @Value("${stripe.status}")
    private String stripePaymentStatus;

    @Override
    public StripePaymentResponseDTO setSomeProperties(Charge charge, StripePaymentRequestDTO stripePaymentRequestDTO) {
        StripePaymentResponseDTO stripePaymentResponseDTO = new StripePaymentResponseDTO();

        stripePaymentResponseDTO = modelMapper.map(stripePaymentRequestDTO, StripePaymentResponseDTO.class);
        stripePaymentResponseDTO.setReceiptUrl(charge.getReceiptUrl());
        stripePaymentResponseDTO.setBalanceTransactionId(charge.getBalanceTransaction());
        stripePaymentResponseDTO.setPaymentid(charge.getId());
        stripePaymentResponseDTO.setPaymentStatus(getPaymentStatus(charge.getStatus()));

        return stripePaymentResponseDTO;
    }

    @Override
    public PaymentStatus getPaymentStatus(String status) {
        if (status.equalsIgnoreCase(stripePaymentStatus))
            return PaymentStatus.SUCCESS;

        return PaymentStatus.FAIL;
    }
}
