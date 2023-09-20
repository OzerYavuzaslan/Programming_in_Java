package com.ozeryavuzaslan.emailservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailStatus;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailType;
import com.ozeryavuzaslan.emailservice.objectPropertySetter.EmailPropertySetter;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    @Override //TODO: partOfTheBodyMessage ArrayList yap.
    public void setSomeProperties(EmailDTO emailDTO, String mailTo, String mailCc, String partOfTheBodyMessage, EmailType emailType) {
        emailDTO.setMailTo(mailTo);
        emailDTO.setMailCc(mailCc);
        emailDTO.setEmailType(emailType);

        String tmpSubject = null;
        String tmpBody = null;

        switch (emailType) {
            case STOCK -> {
                tmpSubject = emailStockSubject;
                tmpBody = partOfTheBodyMessage + " " + emailStockBody;
            }
            case ORDER -> {
                tmpSubject = emailPaymentSubject;
                tmpBody = emailOrderBody;
            }
            case PAYMENT -> {
                tmpSubject = emailOrderSubject;
                tmpBody = emailPaymentBody;
            }
        };

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