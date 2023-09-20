package com.ozeryavuzaslan.basedomains.dto.emails;

import com.ozeryavuzaslan.basedomains.dto.enums.EmailStatus;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private transient long id;
    private String body;
    private String mailTo;
    private String mailCc;
    private String subject;
    private String mailFrom;
    private String username;
    private String password;
    private String fromMail;
    private EmailType emailType;
    private LocalDateTime sendDate;
    private EmailStatus emailStatus;
}
