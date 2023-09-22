package com.ozeryavuzaslan.paymentservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.enums.PaymentStatus;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.util.DoubleToIntConversion;
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
    private final DoubleToIntConversion doubleToIntConversion;

    @Value("${stripe.status}")
    private String stripePaymentStatus;

    @Override
    public StripePaymentResponseDTO setSomeProperties(Charge charge, StripePaymentRequestDTO stripePaymentRequestDTO) {
        StripePaymentResponseDTO stripePaymentResponseDTO = modelMapper.map(stripePaymentRequestDTO, StripePaymentResponseDTO.class);
        stripePaymentResponseDTO.setPaymentid(charge.getId());
        stripePaymentResponseDTO.setReceiptUrl(charge.getReceiptUrl());
        stripePaymentResponseDTO.setPaymentStatus(getPaymentStatus(charge.getStatus()));
        stripePaymentResponseDTO.setBalanceTransactionId(charge.getBalanceTransaction());

        if (stripePaymentRequestDTO.getCurrencyType().equals(CurrencyType.USD)) {
            stripePaymentResponseDTO.setTotalPrice(doubleToIntConversion.convertDoubleToIntWithoutLosingPrecision(stripePaymentResponseDTO.getTotalPrice()));
            stripePaymentResponseDTO.setTotalPriceWithoutTax(doubleToIntConversion.convertDoubleToIntWithoutLosingPrecision(stripePaymentResponseDTO.getTotalPriceWithoutTax()));
        }

        return stripePaymentResponseDTO;
    }

    @Override
    public PaymentStatus getPaymentStatus(String status) {
        if (status.equalsIgnoreCase(stripePaymentStatus))
            return PaymentStatus.SUCCESS;

        return PaymentStatus.FAIL;
    }
}
