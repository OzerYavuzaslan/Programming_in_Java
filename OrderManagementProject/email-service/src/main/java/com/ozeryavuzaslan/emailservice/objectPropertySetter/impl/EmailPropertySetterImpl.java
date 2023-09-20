package com.ozeryavuzaslan.emailservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailStatus;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailType;
import com.ozeryavuzaslan.emailservice.objectPropertySetter.EmailPropertySetter;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EmailPropertySetterImpl implements EmailPropertySetter {
    @Value("${email.stock.subject}")
    private String emailStockSubject;

    @Value("${email.stock.body}")
    private String emailStockBody;

    @Value("${email.payment.subject}")
    private String emailPaymentSubject;

    @Value("${email.payment.body}")
    private String emailPaymentBody;

    @Value("${email.order.subject}")
    private String emailOrderSubject;

    @Value("${email.order.body}")
    private String emailOrderBody;

    @Value("${hash.map.product.code.key}")
    private String productCodeKey;

    @Value("${hash.map.product.name.key}")
    private String productNameKey;

    @Value("${hash.map.mail.to.key}")
    private String mailToKey;

    @Value("${hash.map.mail.cc.key}")
    private String mailCcKey;

    @Override
    public void setSomeProperties(EmailDTO emailDTO, HashMap<String, String> emailInfoMap, EmailType emailType) {
        emailDTO.setMailTo(emailInfoMap.get(mailToKey));
        emailDTO.setMailCc(emailInfoMap.get(mailCcKey));
        emailDTO.setEmailType(emailType);

        String tmpSubject = null;
        String tmpBody = null;

        switch (emailType) {
            case STOCK -> {
                tmpSubject = emailStockSubject;
                tmpBody = emailInfoMap.get(productCodeKey) + " " + emailInfoMap.get(productNameKey) + " " + emailStockBody;
            }
            case ORDER -> {
                tmpSubject = emailPaymentSubject;
                tmpBody = emailOrderBody;
            }
            case PAYMENT -> {
                tmpSubject = emailOrderSubject;
                tmpBody = emailPaymentBody;
            }
        }

        emailDTO.setBody(tmpBody);
        emailDTO.setSubject(tmpSubject);
    }

    @Override
    public void setSomeProperties(EmailDTO emailDTO) {
        emailDTO.setEmailStatus(EmailStatus.SENT);
    }

    @Override
    public void setSomeProperties(MessagingException exception, EmailDTO emailDTO) {
        emailDTO.setEmailStatus(EmailStatus.FAILED);
        emailDTO.setEmailExceptionMessage(exception.getMessage());
    }
}