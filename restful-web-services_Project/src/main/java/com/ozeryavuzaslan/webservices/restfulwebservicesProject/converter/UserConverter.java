package com.ozeryavuzaslan.webservices.restfulwebservicesProject.converter;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.UserRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.UserResponse;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {
    public UserResponse convert(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setEmail(user.getEmail());
        userResponse.setBirthDate(user.getBirthDate());

        return userResponse;
    }

    public User convert(UserRequest userRequest){
        User user = new User();

        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setEmail(userRequest.getEmail());
        user.setBirthDate(userRequest.getBirthDate());

        return user;
    }

    public User convert(UserRequest userRequest, User user){
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setEmail(userRequest.getEmail());
        user.setBirthDate(userRequest.getBirthDate());

        return user;
    }

    public List<UserResponse> convert(List<User> userList) {
        List<UserResponse> userResponse = new ArrayList<>();

        for (User user : userList)
            userResponse.add(convert(user));

        return userList.stream().map(this::convert).toList();
    }
}
