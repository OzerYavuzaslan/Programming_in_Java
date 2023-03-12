package com.biletimcepte.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull
    private String email;

    @NotNull
    private String password;

    @Nullable
    private String username;
}