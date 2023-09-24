package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.*;
import com.ozeryavuzaslan.basedomains.util.NumericalTypeConversion;
import com.ozeryavuzaslan.paymentservice.dto.PaymentInvoiceDTO;
import com.ozeryavuzaslan.paymentservice.exception.PaymentNotFoundException;
import com.ozeryavuzaslan.paymentservice.exception.RefundAmountExceedsException;
import com.ozeryavuzaslan.paymentservice.model.PaymentInvoice;
import com.ozeryavuzaslan.paymentservice.objectPropertySetter.SetSomePaymentProperties;
import com.ozeryavuzaslan.paymentservice.repository.PaymentRepository;
import com.ozeryavuzaslan.paymentservice.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripePaymentServiceImpl implements PaymentService<StripePaymentRequestDTO, StripePaymentResponseDTO, StripeRefundRequestDTO, StripeRefundResponseDTO> {
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;
    private final PaymentRepository paymentRepository;
    private final NumericalTypeConversion numericalTypeConversion;
    private final SetSomePaymentProperties setSomePaymentProperties;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${rabbit.payment.email.queue.name}")
    private String paymentServiceQueueName;

    @Value("${rabbit.refund.email.queue.name}")
    private String refundServiceQueueName;

    @Value("${refund.request.exceeds.exception.message}")
    private String refundRequestExceedsTheActualPayment;

    @Value("${payment.not.found.exception.message}")
    private String paymentNotFoundExceptionMsg;

    @PostConstruct
    private void init(){
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    public StripePaymentResponseDTO pay(StripePaymentRequestDTO stripePaymentRequestDTO) throws StripeException {
        Charge charge = stripePayment(stripePaymentRequestDTO);
        StripePaymentResponseDTO stripePaymentResponseDTO = setSomePaymentProperties.setSomeProperties(charge, stripePaymentRequestDTO);
        modelMapper.map(paymentRepository.save(modelMapper.map(stripePaymentResponseDTO, PaymentInvoice.class)), stripePaymentResponseDTO);
        rabbitTemplate.convertAndSend(paymentServiceQueueName, modelMapper.map(stripePaymentResponseDTO, PaymentResponseForAsyncMsgDTO.class));
        return stripePaymentResponseDTO;
    }

    @Override
    public StripeRefundResponseDTO refund(StripeRefundRequestDTO stripeRefundRequestDTO) throws StripeException {
        PaymentInvoiceDTO paymentInvoiceDTO = modelMapper.map(paymentRepository.findByOrderid(stripeRefundRequestDTO.getOrderid()).orElseThrow(() -> new PaymentNotFoundException(paymentNotFoundExceptionMsg)), PaymentInvoiceDTO.class);
        double tmpRefundRequestAmount = numericalTypeConversion.convertLongToProperDouble(numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripeRefundRequestDTO.getRefundRequestAmount()));
        double tmpRefundedAmount = paymentInvoiceDTO.getRefundedAmount();
        double tmpPaidTotalPrice = paymentInvoiceDTO.getTotalPrice();

        if (tmpRefundRequestAmount + tmpRefundedAmount > tmpPaidTotalPrice)
            throw new RefundAmountExceedsException(refundRequestExceedsTheActualPayment);

        Refund refund = stripeRefund(stripeRefundRequestDTO, paymentInvoiceDTO);
        StripeRefundResponseDTO stripeRefundResponseDTO = setSomePaymentProperties.setSomeProperties(refund, stripeRefundRequestDTO);

        paymentRepository.updatePaymentInvoice(stripeRefundResponseDTO.getOrderid(), stripeRefundResponseDTO.getRefundid(),
                stripeRefundResponseDTO.getRefundedAmount(), stripeRefundResponseDTO.getRefundRequestAmount(),
                stripeRefundResponseDTO.getPaymentType(), LocalDateTime.now());

        rabbitTemplate.convertAndSend(refundServiceQueueName, modelMapper.map(stripeRefundResponseDTO, RefundResponseForAsyncMsgDTO.class));
        return stripeRefundResponseDTO;
    }

    private Charge stripePayment(StripePaymentRequestDTO stripePaymentRequestDTO) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripePaymentRequestDTO.getTotalPrice()));
        chargeParams.put("currency", stripePaymentRequestDTO.getCurrencyType());
        chargeParams.put("customer", stripePaymentRequestDTO.getUserid());
        return Charge.create(chargeParams);
    }

    private Refund stripeRefund(StripeRefundRequestDTO stripeRefundRequestDTO, PaymentInvoiceDTO paymentInvoiceDTO) throws StripeException {
        Map<String, Object> refundParams = new HashMap<>();
        refundParams.put("amount", numericalTypeConversion.convertDoubleToLongWithoutLosingPrecision(stripeRefundRequestDTO.getRefundRequestAmount()));
        refundParams.put("charge", paymentInvoiceDTO.getPaymentid());
        return Refund.create(refundParams);
    }
}