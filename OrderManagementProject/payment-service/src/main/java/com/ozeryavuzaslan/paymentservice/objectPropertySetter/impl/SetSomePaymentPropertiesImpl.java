package com.ozeryavuzaslan.paymentservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentStatus;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentType;
import com.ozeryavuzaslan.basedomains.util.NumericalTypeConversion;
import com.ozeryavuzaslan.paymentservice.objectPropertySetter.SetSomePaymentProperties;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SetSomePaymentPropertiesImpl implements SetSomePaymentProperties {
    private final ModelMapper modelMapper;
    private final NumericalTypeConversion numericalTypeConversion;

    @Value("${stripe.status}")
    private String stripePaymentStatus;

    @Override
    public StripePaymentResponseDTO setSomeProperties(Charge charge, StripePaymentRequestDTO stripePaymentRequestDTO) {
        StripePaymentResponseDTO stripePaymentResponseDTO = modelMapper.map(stripePaymentRequestDTO, StripePaymentResponseDTO.class);
        stripePaymentResponseDTO.setPaymentid(charge.getId());
        stripePaymentResponseDTO.setReceiptUrl(charge.getReceiptUrl());
        stripePaymentResponseDTO.setPaymentStatus(getStatus(charge.getStatus()));
        stripePaymentResponseDTO.setBalanceTransactionId(charge.getBalanceTransaction());
        stripePaymentResponseDTO.setPaymentDate(LocalDateTime.now());
        stripePaymentResponseDTO.setPaymentType(PaymentType.PAYMENT);

        if (stripePaymentRequestDTO.getCurrencyType().equals(CurrencyType.USD)
                || stripePaymentRequestDTO.getCurrencyType().equals(CurrencyType.EUR)) {
            stripePaymentResponseDTO.setTotalPrice(numericalTypeConversion.convertIntToProperDouble(numericalTypeConversion.convertDoubleToIntWithoutLosingPrecision(stripePaymentResponseDTO.getTotalPrice())));
            stripePaymentResponseDTO.setTotalPriceWithoutTax(numericalTypeConversion.convertIntToProperDouble(numericalTypeConversion.convertDoubleToIntWithoutLosingPrecision(stripePaymentResponseDTO.getTotalPriceWithoutTax())));
        }

        return stripePaymentResponseDTO;
    }

    @Override
    public StripeRefundResponseDTO setSomeProperties(Refund refund, StripeRefundRequestDTO stripeRefundRequestDTO){
        StripeRefundResponseDTO stripeRefundResponseDTO = modelMapper.map(stripeRefundRequestDTO, StripeRefundResponseDTO.class);
        stripeRefundResponseDTO.setRefundid(refund.getId());
        stripeRefundResponseDTO.setPaymentStatus(getStatus(refund.getStatus()));
        stripeRefundResponseDTO.setPaymentid(refund.getCharge());
        stripeRefundResponseDTO.setBalanceTransactionId(refund.getBalanceTransaction());
        stripeRefundResponseDTO.setRefundDate(LocalDateTime.now());
        stripeRefundResponseDTO.setPaymentType(PaymentType.REFUND);

        if (stripeRefundRequestDTO.getCurrencyType().equals(CurrencyType.USD)
                || stripeRefundRequestDTO.getCurrencyType().equals(CurrencyType.EUR)) {
            stripeRefundResponseDTO.setRefundRequestAmount(numericalTypeConversion.convertIntToProperDouble(numericalTypeConversion.convertDoubleToIntWithoutLosingPrecision(stripeRefundResponseDTO.getRefundRequestAmount())));
            stripeRefundResponseDTO.setRefundedAmount(numericalTypeConversion.convertIntToProperDouble(numericalTypeConversion.convertDoubleToIntWithoutLosingPrecision(stripeRefundResponseDTO.getRefundedAmount())));
        }

        return stripeRefundResponseDTO;
    }

    @Override
    public PaymentStatus getStatus(String status) {
        if (status.equalsIgnoreCase(stripePaymentStatus))
            return PaymentStatus.SUCCESS;

        return PaymentStatus.FAIL;
    }
}
