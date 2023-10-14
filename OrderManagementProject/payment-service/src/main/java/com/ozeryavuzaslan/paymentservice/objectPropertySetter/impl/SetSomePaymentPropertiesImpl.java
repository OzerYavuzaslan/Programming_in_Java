package com.ozeryavuzaslan.paymentservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.CurrencyType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentStatus;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentType;
import com.ozeryavuzaslan.basedomains.util.NumericalTypeConversion;
import com.ozeryavuzaslan.paymentservice.dto.PaymentInvoiceDTO;
import com.ozeryavuzaslan.paymentservice.exception.StripeCustomerNotFound;
import com.ozeryavuzaslan.paymentservice.objectPropertySetter.SetSomePaymentProperties;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.Refund;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SetSomePaymentPropertiesImpl implements SetSomePaymentProperties {
    private final ModelMapper modelMapper;
    private final Map<String, Object> stripeParams = new HashMap<>();
    private final NumericalTypeConversion numericalTypeConversion;

    @Value("${stripe.status}")
    private String stripePaymentStatus;

    @Value("${stripe.customer.not.found.exception.message}")
    private String customerNotFound;

    @Override
    public StripePaymentResponseDTO setSomeProperties(Charge charge, StripePaymentRequestDTO stripePaymentRequestDTO) {
        StripePaymentResponseDTO stripePaymentResponseDTO = modelMapper.map(stripePaymentRequestDTO, StripePaymentResponseDTO.class);
        stripePaymentResponseDTO.setPaymentid(charge.getId());
        stripePaymentResponseDTO.setUserid(charge.getCustomer());
        stripePaymentResponseDTO.setReceiptUrl(charge.getReceiptUrl());
        stripePaymentResponseDTO.setPaymentStatus(getStatus(charge.getStatus()));
        stripePaymentResponseDTO.setBalanceTransactionId(charge.getBalanceTransaction());
        stripePaymentResponseDTO.setPaymentDate(LocalDateTime.now());
        stripePaymentResponseDTO.setPaymentType(PaymentType.PAYMENT);

        if (stripePaymentRequestDTO.getCurrencyType().equals(CurrencyType.USD)
                || stripePaymentRequestDTO.getCurrencyType().equals(CurrencyType.EUR)) {
            stripePaymentResponseDTO.setTotalPriceWithDiscount(numericalTypeConversion.convertLongToProperDouble(numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripePaymentResponseDTO.getTotalPriceWithDiscount())));
            stripePaymentResponseDTO.setTotalPriceWithDiscountWithoutTax(numericalTypeConversion.convertLongToProperDouble(numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripePaymentResponseDTO.getTotalPriceWithDiscountWithoutTax())));
            stripePaymentResponseDTO.setTotalPrice(numericalTypeConversion.convertLongToProperDouble(numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripePaymentResponseDTO.getTotalPrice())));
            stripePaymentResponseDTO.setTotalPriceWithoutTax(numericalTypeConversion.convertLongToProperDouble(numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripePaymentResponseDTO.getTotalPriceWithoutTax())));
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
            stripeRefundResponseDTO.setRefundRequestAmount(numericalTypeConversion.convertLongToProperDouble(numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripeRefundResponseDTO.getRefundRequestAmount())));
            stripeRefundResponseDTO.setRefundedAmount(numericalTypeConversion.convertLongToProperDouble(refund.getAmount()));
        }

        return stripeRefundResponseDTO;
    }

    @Override
    public PaymentStatus getStatus(String status) {
        if (status.equalsIgnoreCase(stripePaymentStatus))
            return PaymentStatus.SUCCESS;

        return PaymentStatus.FAIL;
    }

    @Override
    public Map<String, Object> setSomeProperties(StripePaymentRequestDTO stripePaymentRequestDTO) throws StripeException {
        stripeParams.put("email", stripePaymentRequestDTO.getEmail());
        String customerId;

        try {
            customerId = getCustomer().getData().get(0).getId();
        } catch (IndexOutOfBoundsException exception){
            throw new StripeCustomerNotFound(customerNotFound + " " + stripePaymentRequestDTO.getEmail());
        }

        stripeParams.clear();
        stripeParams.put("customer", customerId);

        if (stripePaymentRequestDTO.getTotalPriceWithDiscount() < stripePaymentRequestDTO.getTotalPrice())
            stripeParams.put("amount", numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripePaymentRequestDTO.getTotalPriceWithDiscount()));
        else
            stripeParams.put("amount", numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripePaymentRequestDTO.getTotalPrice()));

        stripeParams.put("currency", stripePaymentRequestDTO.getCurrencyType());
        return stripeParams;
    }

    @Override
    public Map<String, Object> setSomeProperties(StripeRefundRequestDTO stripeRefundRequestDTO, PaymentInvoiceDTO paymentInvoiceDTO) {
        stripeParams.put("amount", numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripeRefundRequestDTO.getRefundRequestAmount()));
        stripeParams.put("charge", paymentInvoiceDTO.getPaymentid());
        return stripeParams;
    }

    private CustomerCollection getCustomer() throws StripeException {
        return Customer.list(stripeParams);
    }
}
