package com.ozeryavuzaslan.webservices.restfulwebservicesProject.service;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.UserRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse save(UserRequest userRequest);
    UserResponse findOne(int id);
    UserResponse findOneByEmail(String eMail);
    void deleteById(int id);
    UserResponse updateByEmail(UserRequest userRequest);
    UserResponse updateById(int id, UserRequest userRequest);
}
