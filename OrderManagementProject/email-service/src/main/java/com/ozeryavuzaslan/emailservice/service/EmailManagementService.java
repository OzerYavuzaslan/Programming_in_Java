package com.ozeryavuzaslan.emailservice.service;

import com.ozeryavuzaslan.emailservice.dto.EmailDTO;

public interface EmailManagementService {
    EmailDTO sendEmail(EmailDTO emailDTO);
}
