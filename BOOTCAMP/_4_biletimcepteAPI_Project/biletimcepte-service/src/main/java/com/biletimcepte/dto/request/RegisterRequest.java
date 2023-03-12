package com.biletimcepte.dto.request;

import com.biletimcepte.model.enums.GenderType;
import com.biletimcepte.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private int age;
    private GenderType genderType;
    private UserType userType;
    private String phoneNumber;
}