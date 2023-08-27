package com.ozeryavuzaslan.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private String username;
    private String password;
    private String from;
    private String subject;
    private String body;
}