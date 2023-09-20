package com.ozeryavuzaslan.basedomains.util;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;

public interface EmailManagementService {
    EmailDTO sendEmail(EmailDTO emailDTO);
}
