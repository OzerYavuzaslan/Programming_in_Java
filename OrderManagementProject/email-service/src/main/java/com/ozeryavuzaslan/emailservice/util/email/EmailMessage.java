package com.ozeryavuzaslan.emailservice.util.email;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailMessage{
    public Message getMessage(Session session, EmailDTO emailDTO) throws MessagingException {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(emailDTO.getMailFrom()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDTO.getMailTo()));

        if (emailDTO.getMailCc() != null)
            message.setRecipient(Message.RecipientType.CC, new InternetAddress(emailDTO.getMailCc()));

        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getBody(), "UTF-8", "html");

        return message;
    }
}
