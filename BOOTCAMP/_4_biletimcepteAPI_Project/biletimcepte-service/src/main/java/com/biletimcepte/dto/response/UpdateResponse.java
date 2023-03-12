package com.biletimcepte.dto.response;

import com.biletimcepte.model.enums.GenderType;
import com.biletimcepte.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResponse {
    private String username;
    private String name;
    private String surname;
    private String email;
    private GenderType genderType;
    private UserType userType;
    private int age;
    private String updateMessage;
    private String phoneNumber;
}
