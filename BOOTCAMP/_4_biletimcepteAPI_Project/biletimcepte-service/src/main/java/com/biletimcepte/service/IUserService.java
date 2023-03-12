package com.biletimcepte.service;

import com.biletimcepte.dto.request.RegisterRequest;
import com.biletimcepte.dto.response.UpdateResponse;
import com.biletimcepte.dto.response.UserResponse;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface IUserService {
    List<UserResponse> listUsers();
    UserResponse registerUser(RegisterRequest registerRequest) throws InvalidKeySpecException, NoSuchAlgorithmException;
    UpdateResponse updateUser(RegisterRequest registerRequest) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
