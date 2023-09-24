package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripeRefundResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.RefundResponseDTO;
import com.ozeryavuzaslan.paymentservice.exception.PaymentNotFoundException;
import com.ozeryavuzaslan.paymentservice.exception.RefundNotFoundException;
import com.ozeryavuzaslan.paymentservice.repository.PaymentRepository;
import com.ozeryavuzaslan.paymentservice.service.PaymentFinderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentFinderServiceImpl<R extends PaymentResponseDTO, N extends RefundResponseDTO> implements PaymentFinderService<R, N> {
    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;

    @Value("${payment.not.found.exception.message}")
    private String paymentNotFoundExceptionMsg;

    @Value("${refund.not.found.exception.message}")
    private String refundNotFoundExceptionMsg;

    @Override
    @SuppressWarnings("unchecked")
    public R getPayment(long orderid) {
        return (R) modelMapper.map(paymentRepository
                .findByOrderid(orderid)
                .orElseThrow(() -> new PaymentNotFoundException(paymentNotFoundExceptionMsg)), StripePaymentResponseDTO.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public N getRefund(long orderid) {
        return (N) modelMapper.map(paymentRepository
                .findByOrderid(orderid)
                .orElseThrow(() -> new RefundNotFoundException(refundNotFoundExceptionMsg)), StripeRefundResponseDTO.class);
    }
}
