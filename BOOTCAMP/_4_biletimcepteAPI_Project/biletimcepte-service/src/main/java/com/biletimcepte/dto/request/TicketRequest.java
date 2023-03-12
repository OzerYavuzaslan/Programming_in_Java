package com.biletimcepte.dto.request;

import com.biletimcepte.model.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private String nationalIdentityNumber;
    private String passengerName;
    private String passengerSurname;
    private int passengerAge;
    private String passengerEmail;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;
}
