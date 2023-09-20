package com.ozeryavuzaslan.emailservice.model;

import com.ozeryavuzaslan.basedomains.dto.enums.EmailStatus;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "emails",
        indexes = {
                @Index(name = "to_index", columnList = "mailTo"),
                @Index(name = "from_index", columnList = "mailFrom"),
                @Index(name = "cc_index", columnList = "mailCc")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String mailTo;

    @Column(nullable = false)
    private String mailFrom;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailType emailType;

    @Column(columnDefinition = "TEXT")
    private String body;

    private String mailCc;
    private String subject;
    private String emailExceptionMessage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailStatus emailStatus;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendDate;
}
