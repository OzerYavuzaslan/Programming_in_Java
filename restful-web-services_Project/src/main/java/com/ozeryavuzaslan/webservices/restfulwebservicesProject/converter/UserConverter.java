package com.ozeryavuzaslan.webservices.restfulwebservicesProject.converter;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.UserRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convert(UserRequest userRequest, User user){
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setEmail(userRequest.getEmail());
        user.setBirthDate(userRequest.getBirthDate());

        return user;
    }
}
