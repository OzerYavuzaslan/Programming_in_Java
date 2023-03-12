package com.biletimcepte.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
}
