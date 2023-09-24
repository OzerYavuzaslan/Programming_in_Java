package com.ozeryavuzaslan.emailservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.emails.enums.EmailType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentType;
import jakarta.mail.MessagingException;

import java.util.HashMap;

public interface EmailPropertySetter {
    void setSomeProperties(MessagingException exception, EmailDTO emailDTO);
    void setSomeProperties(EmailDTO emailDTO);
    void setSomeProperties (EmailDTO emailDTO, HashMap<String, String> emailInfoMap, EmailType emailType, PaymentType paymentType);
}