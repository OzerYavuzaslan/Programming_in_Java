package com.ozeryavuzaslan.emailservice.util.email;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderEventDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceUtil {
    private final EmailDTO emailDTO;
    private final EmailMessage emailMessage;
    private final EmailProperties emailProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceUtil.class);

    public void sendEmail(OrderEventDTO orderEventDTO){
        Session session = Session.getInstance(emailProperties.getEmailProperties(), new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailDTO.getUsername(), emailDTO.getPassword());
            }
        });

        try {
            Transport.send(emailMessage.getMessage(session, emailDTO, orderEventDTO.getOrderDTO()));
            LOGGER.info(String.format("Email Message Sent Successfully --> %s", orderEventDTO.getOrderDTO().getEmail()));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}