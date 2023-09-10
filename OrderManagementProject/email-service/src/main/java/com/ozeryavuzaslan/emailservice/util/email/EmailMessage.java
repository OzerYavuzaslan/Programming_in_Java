package com.ozeryavuzaslan.emailservice.util.email;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailMessage {
    public Message getMessage(Session session, EmailDTO emailDTO, OrderDTO orderDTO) throws MessagingException {
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(emailDTO.getFrom()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(orderDTO.getEmail()));
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getBody());

        return message;
    }
}
