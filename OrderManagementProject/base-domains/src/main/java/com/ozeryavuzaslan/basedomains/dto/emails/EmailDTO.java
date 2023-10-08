package com.ozeryavuzaslan.basedomains.dto.emails;

import com.ozeryavuzaslan.basedomains.dto.emails.enums.EmailStatus;
import com.ozeryavuzaslan.basedomains.dto.emails.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO implements Serializable {
    private long id;
    private String body;
    private String mailTo;
    private String mailCc;
    private String subject;
    private String mailFrom;
    private EmailType emailType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendDate;

    private EmailStatus emailStatus;
    private transient String username;
    private transient String password;
    private String emailExceptionMessage;
}
