package com.ozeryavuzaslan.emailservice.service;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;

public interface EmailManagementService {
    EmailDTO sendEmail(EmailDTO emailDTO);
}
