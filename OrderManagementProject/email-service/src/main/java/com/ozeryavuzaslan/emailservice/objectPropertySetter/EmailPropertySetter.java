package com.ozeryavuzaslan.emailservice.objectPropertySetter;

import com.ozeryavuzaslan.emailservice.dto.EmailDTO;
import com.ozeryavuzaslan.emailservice.dto.enums.EmailType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentType;
import jakarta.mail.MessagingException;

import java.util.HashMap;

public interface EmailPropertySetter {
    void setSomeProperties(MessagingException exception, EmailDTO emailDTO);
    void setSomeProperties(EmailDTO emailDTO);
    void setSomeProperties (EmailDTO emailDTO, HashMap<String, String> emailInfoMap, EmailType emailType, PaymentType paymentType);
}