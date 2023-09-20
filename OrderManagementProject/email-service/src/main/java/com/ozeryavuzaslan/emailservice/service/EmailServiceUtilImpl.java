package com.ozeryavuzaslan.emailservice.service;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.util.EmailManagementService;
import com.ozeryavuzaslan.emailservice.model.Email;
import com.ozeryavuzaslan.emailservice.objectPropertySetter.EmailPropertySetter;
import com.ozeryavuzaslan.emailservice.repository.EmailRepository;
import com.ozeryavuzaslan.emailservice.util.email.EmailMessage;
import com.ozeryavuzaslan.emailservice.util.email.EmailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceUtilImpl implements EmailManagementService{
    private final EmailMessage emailMessage;
    private final ModelMapper modelMapper;
    private final EmailRepository emailRepository;
    private final EmailProperties emailProperties;
    private final EmailPropertySetter emailPropertySetter;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceUtilImpl.class);

    @Override
    public EmailDTO sendEmail(EmailDTO emailDTO) {
        Session session = Session.getInstance(emailProperties.getEmailProperties(), new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailDTO.getUsername(), emailDTO.getPassword());
            }
        });

        try {
            Transport.send(emailMessage.getMessage(session, emailDTO));
            LOGGER.info(String.format("Email Message Sent Successfully --> %s", emailDTO.getEmailType()));
            return emailDTO;
        } catch (MessagingException exception) {
            emailPropertySetter.setSomeProperties(exception, emailDTO);
            emailRepository.save(modelMapper.map(emailDTO, Email.class));
            throw new RuntimeException(exception);
        }
    }
}