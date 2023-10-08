package com.ozeryavuzaslan.emailservice.service.rabbitmq;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.emails.enums.EmailType;
import com.ozeryavuzaslan.basedomains.dto.payments.RefundResponseForAsyncMsgDTO;
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
public class EmailRefundListener {
    private final EmailDTO emailDTO;
    private final ModelMapper modelMapper;
    private final EmailRepository emailRepository;
    private final EmailPropertySetter emailPropertySetter;
    private final EmailManagementService emailManagementService;

    @Value("${hash.map.email.to.key}")
    private String mailToKey;

    @Value("${hash.map.email.cc.key}")
    private String mailCcKey;

    @Value("${hash.map.email.body.full.name}")
    private String fullName;

    @Value("${hash.map.email.body.payment.currency.type}")
    private String currencyType;

    @Value("${hash.map.email.body.refund.date}")
    private String refundDate;

    @Value("${hash.map.email.body.refund.refunded.amount}")
    private String refundedAmount;

    @Value("${hash.map.email.body.refund.refunded.amount}")
    private String refundRequestAmount;

    @RabbitListener(queues = "${rabbit.refund.email.queue.name}")
    public void refundListener(RefundResponseForAsyncMsgDTO refundResponseForAsyncMsgDTO) {
        HashMap<String, String> paymentResponseDTOMap = new HashMap<>();
        paymentResponseDTOMap.put(mailToKey, refundResponseForAsyncMsgDTO.getEmail());
        paymentResponseDTOMap.put(mailCcKey, null);

        switch (refundResponseForAsyncMsgDTO.getPaymentProviderType()){
            case STRIPE -> {
                paymentResponseDTOMap.put(fullName, refundResponseForAsyncMsgDTO.getName() + " " + refundResponseForAsyncMsgDTO.getSurname());
                paymentResponseDTOMap.put(refundedAmount, String.valueOf(refundResponseForAsyncMsgDTO.getRefundedAmount()));
                paymentResponseDTOMap.put(refundRequestAmount, String.valueOf(refundResponseForAsyncMsgDTO.getRefundRequestAmount()));
                paymentResponseDTOMap.put(currencyType, refundResponseForAsyncMsgDTO.getCurrencyType().toString());
                paymentResponseDTOMap.put(refundDate, refundResponseForAsyncMsgDTO.getRefundDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            case PAYPAL -> {}
            case CREDIT_CARD -> {}
        }

        emailPropertySetter.setSomeProperties(emailDTO, paymentResponseDTOMap, EmailType.PAYMENT, refundResponseForAsyncMsgDTO.getPaymentType());
        emailManagementService.sendEmail(emailDTO);
        emailPropertySetter.setSomeProperties(emailDTO);
        emailRepository.save(modelMapper.map(emailDTO, Email.class));
        System.err.println("Refund Message --> " + refundResponseForAsyncMsgDTO);
    }
}
