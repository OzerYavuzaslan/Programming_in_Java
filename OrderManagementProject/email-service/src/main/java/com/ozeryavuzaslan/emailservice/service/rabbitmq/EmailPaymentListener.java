package com.ozeryavuzaslan.emailservice.service.rabbitmq;

import com.ozeryavuzaslan.emailservice.dto.EmailDTO;
import com.ozeryavuzaslan.emailservice.dto.enums.EmailType;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentResponseForAsyncMsgDTO;
import com.ozeryavuzaslan.emailservice.service.EmailManagementService;
import com.ozeryavuzaslan.emailservice.model.Email;
import com.ozeryavuzaslan.emailservice.objectPropertySetter.EmailPropertySetter;
import com.ozeryavuzaslan.emailservice.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
@EnableRabbit
@RequiredArgsConstructor
public class EmailPaymentListener {
    private final EmailDTO emailDTO;
    private final ModelMapper modelMapper;
    private final EmailRepository emailRepository;
    private final EmailPropertySetter emailPropertySetter;
    private final EmailManagementService emailManagementService;

    @Value("${hash.map.email.to.key}")
    private String mailToKey;

    @Value("${hash.map.email.cc.key}")
    private String mailCcKey;

    @Value("${hash.map.email.body.stripe.payment.receipt.url}")
    private String receiptUrl;

    @Value("${hash.map.email.body.full.name}")
    private String fullName;

    @Value("${hash.map.email.body.payment.total.price}")
    private String totalPrice;

    @Value("${hash.map.email.body.payment.currency.type}")
    private String currencyType;

    @Value("${hash.map.email.body.payment.date}")
    private String paymentDate;

    @RabbitListener(queues = "${rabbit.payment.email.queue.name}")
    public void paymentListener(PaymentResponseForAsyncMsgDTO paymentResponseForAsyncMsgDTO) {
        HashMap<String, String> paymentResponseDTOMap = new HashMap<>();
        paymentResponseDTOMap.put(mailToKey, paymentResponseForAsyncMsgDTO.getEmail());
        paymentResponseDTOMap.put(mailCcKey, null);

        switch (paymentResponseForAsyncMsgDTO.getPaymentProviderType()){
            case STRIPE -> {
                paymentResponseDTOMap.put(receiptUrl, paymentResponseForAsyncMsgDTO.getStripePaymentResponseDTO().getReceiptUrl());
                paymentResponseDTOMap.put(fullName, paymentResponseForAsyncMsgDTO.getName() + " " + paymentResponseForAsyncMsgDTO.getSurname());
                paymentResponseDTOMap.put(totalPrice, String.valueOf(paymentResponseForAsyncMsgDTO.getTotalPriceWithDiscount()));
                paymentResponseDTOMap.put(currencyType, paymentResponseForAsyncMsgDTO.getCurrencyType().toString());
                paymentResponseDTOMap.put(paymentDate, paymentResponseForAsyncMsgDTO.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            case PAYPAL -> {}
            case CREDIT_CARD -> {}
        }

        emailPropertySetter.setSomeProperties(emailDTO, paymentResponseDTOMap, EmailType.PAYMENT, paymentResponseForAsyncMsgDTO.getPaymentType());
        emailManagementService.sendEmail(emailDTO);
        emailPropertySetter.setSomeProperties(emailDTO);
        emailRepository.save(modelMapper.map(emailDTO, Email.class));
        System.err.println("Payment Message --> " + paymentResponseForAsyncMsgDTO);
    }
}
