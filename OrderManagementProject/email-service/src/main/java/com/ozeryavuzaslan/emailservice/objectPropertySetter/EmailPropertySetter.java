package com.ozeryavuzaslan.emailservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailType;
import jakarta.mail.MessagingException;

import java.util.HashMap;

public interface EmailPropertySetter {
    void setSomeProperties(MessagingException exception, EmailDTO emailDTO);
    void setSomeProperties(EmailDTO emailDTO);
    void setSomeProperties (EmailDTO emailDTO, HashMap<String, String> emailInfoMap, EmailType emailType);
}