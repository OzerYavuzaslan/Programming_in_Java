package com.ozeryavuzaslan.orderservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.*;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.PaymentPropertySetter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPropertySetterImpl implements PaymentPropertySetter {
    private final ModelMapper modelMapper;

    @Override
    public void setSomeProperties(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService) {
        switch (orderDTO.getPaymentProviderType()){
            case STRIPE -> {
                paymentRequestDTOForPaymentService.setStripePaymentRequestDTO(modelMapper.map(orderDTO, StripePaymentRequestDTO.class));
                paymentRequestDTOForPaymentService.getStripePaymentRequestDTO().setOrderid(orderDTO.getId());
            }
            case PAYPAL -> {
                paymentRequestDTOForPaymentService.setPaypalPaymentRequestDTO(modelMapper.map(orderDTO, PaypalPaymentRequestDTO.class));
                paymentRequestDTOForPaymentService.getPaypalPaymentRequestDTO().setOrderid(orderDTO.getId());
            }
            case CREDIT_CARD -> {
                paymentRequestDTOForPaymentService.setCreditCardPaymentRequestDTO(modelMapper.map(orderDTO, CreditCardPaymentRequestDTO.class));
                paymentRequestDTOForPaymentService.getCreditCardPaymentRequestDTO().setOrderid(orderDTO.getId());
            }
        }
    }
}
