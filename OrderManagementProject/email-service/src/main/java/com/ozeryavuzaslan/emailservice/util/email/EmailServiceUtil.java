package com.ozeryavuzaslan.emailservice.util.email;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.util.EmailManagementService;
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
public class EmailServiceUtil implements EmailManagementService{
    private final EmailMessage emailMessage;
    private final EmailProperties emailProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceUtil.class);

    @Override
    public void sendEmail(EmailDTO emailDTO) {
        Session session = Session.getInstance(emailProperties.getEmailProperties(), new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailDTO.getUsername(), emailDTO.getPassword());
            }
        });

        try {
            Transport.send(emailMessage.getMessage(session, emailDTO));
            LOGGER.info(String.format("Email Message Sent Successfully --> %s", emailDTO.getEmailType()));
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }
}