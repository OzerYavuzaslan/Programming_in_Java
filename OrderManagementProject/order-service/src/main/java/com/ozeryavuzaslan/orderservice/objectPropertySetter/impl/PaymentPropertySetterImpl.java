package com.ozeryavuzaslan.orderservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.*;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PaymentPropertySetterImpl implements PaymentPropertySetter {
    private final ModelMapper modelMapper;

    @Override
    public void setSomeProperties(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService) {
        switch (orderDTO.getPaymentProviderType()){
            case STRIPE -> paymentRequestDTOForPaymentService.setStripePaymentRequestDTO(modelMapper.map(orderDTO, StripePaymentRequestDTO.class));
            case PAYPAL -> paymentRequestDTOForPaymentService.setPaypalPaymentRequestDTO(modelMapper.map(orderDTO, PaypalPaymentRequestDTO.class));
            case CREDIT_CARD -> paymentRequestDTOForPaymentService.setCreditCardPaymentRequestDTO(modelMapper.map(orderDTO, CreditCardPaymentRequestDTO.class));
        }
    }

    @Override
    public void setSomeProperties(OrderDTO orderDTO, RefundRequestDTOForPaymentService refundRequestDTOForPaymentService) {
        switch (orderDTO.getPaymentProviderType()){
            case STRIPE -> modelMapper.map(orderDTO, refundRequestDTOForPaymentService.getStripeRefundRequestDTO());
            case PAYPAL, CREDIT_CARD -> {}
        }
    }
}
