package com.ozeryavuzaslan.paymentservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.payments.*;
import com.ozeryavuzaslan.basedomains.util.NumericalTypeConversion;
import com.ozeryavuzaslan.paymentservice.exception.RefundAmountExceedsException;
import com.ozeryavuzaslan.paymentservice.model.PaymentInvoice;
import com.ozeryavuzaslan.paymentservice.objectPropertySetter.SetSomePaymentProperties;
import com.ozeryavuzaslan.paymentservice.repository.PaymentRepository;
import com.ozeryavuzaslan.paymentservice.service.PaymentFinderService;
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
    private final PaymentFinderService<StripePaymentResponseDTO, StripeRefundResponseDTO> paymentFinderService;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${rabbit.payment.email.queue.name}")
    private String paymentServiceQueueName;

    @Value("${rabbit.refund.email.queue.name}")
    private String refundServiceQueueName;

    @Value("${refund.request.exceeds.exception.message}")
    private String refundRequestExceedsTheActualPayment;

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
        //TODO: Eğer ödeme USD ile yapılmış EUR olarak geri ödeme isteniyorsa ya da tam tersi, ona göre o günün kurunu bulup ve kur dönüşümü yapıp ona göre request yapmalı

        StripePaymentResponseDTO stripePaymentResponseDTO = paymentFinderService.getPayment(stripeRefundRequestDTO.getOrderid());
        double tmpRefundRequestAmount = numericalTypeConversion.convertIntToProperDouble(numericalTypeConversion.convertDoubleToIntWithoutLosingPrecision(stripeRefundRequestDTO.getRefundRequestAmount()));

        //TODO: Aşamalı refundlar için daha önce ne kadar refund aldığını hesaplayıp ona göre burada istenilen refund ile ödenebilecek refund arasındaki farkı kontrol et.
        if (tmpRefundRequestAmount > stripePaymentResponseDTO.getTotalPrice())
            throw new RefundAmountExceedsException(refundRequestExceedsTheActualPayment);

        Refund refund = stripeRefund(stripeRefundRequestDTO, stripePaymentResponseDTO);
        StripeRefundResponseDTO stripeRefundResponseDTO = setSomePaymentProperties.setSomeProperties(refund, stripeRefundRequestDTO);
        modelMapper.map(paymentRepository.save(modelMapper.map(stripeRefundResponseDTO, PaymentInvoice.class)), stripeRefundResponseDTO);
        rabbitTemplate.convertAndSend(refundRequestExceedsTheActualPayment, modelMapper.map(stripeRefundResponseDTO, RefundResponseForAsyncMsgDTO.class));
        return stripeRefundResponseDTO;
    }

    private Charge stripePayment(StripePaymentRequestDTO stripePaymentRequestDTO) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", numericalTypeConversion.convertDoubleToIntWithoutLosingPrecision(stripePaymentRequestDTO.getTotalPrice()));
        chargeParams.put("currency", stripePaymentRequestDTO.getCurrencyType());
        chargeParams.put("customer", stripePaymentRequestDTO.getUserid());
        return Charge.create(chargeParams);
    }

    private Refund stripeRefund(StripeRefundRequestDTO stripeRefundRequestDTO, StripePaymentResponseDTO stripePaymentResponseDTO) throws StripeException {
        Map<String, Object> refundParams = new HashMap<>();
        refundParams.put("amount", numericalTypeConversion.convertDoubleToIntWithoutLosingPrecision(stripeRefundRequestDTO.getRefundRequestAmount()));
        refundParams.put("currency", stripeRefundRequestDTO.getCurrencyType());
        refundParams.put("customer", stripeRefundRequestDTO.getUserid());
        refundParams.put("charge", stripePaymentResponseDTO.getPaymentid());
        refundParams.put("balance_transaction", stripePaymentResponseDTO.getBalanceTransactionId());
        return Refund.create(refundParams);
    }
}