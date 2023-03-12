package com.biletimcepte.converter;

import com.biletimcepte.dto.request.RegisterRequest;
import com.biletimcepte.dto.response.UpdateResponse;
import com.biletimcepte.dto.response.UserResponse;
import com.biletimcepte.model.Role;
import com.biletimcepte.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserConverter {
    public UserResponse convert(User user) {
        UserResponse response = new UserResponse();

        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setUsername(user.getUsername());
        response.setSurname(user.getSurname());

        return response;
    }

    public User convert(RegisterRequest registerRequest, String hashedPassword, Set<Role> roleSet) {
        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setUsername(registerRequest.getUsername());
        user.setCreateDate(LocalDateTime.now());
        user.setGenderType(registerRequest.getGenderType());
        user.setAge(registerRequest.getAge());
        user.setSurname(registerRequest.getSurname());
        user.setUserType(registerRequest.getUserType());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPassword(hashedPassword);
        user.setRoles(roleSet);

        return user;
    }

    public User convert(User specificUser, RegisterRequest registerRequest, String hashedPassword){
        specificUser.setUsername(registerRequest.getUsername());
        specificUser.setPassword(hashedPassword);
        specificUser.setEmail(registerRequest.getEmail());
        specificUser.setName(registerRequest.getName());
        specificUser.setSurname(registerRequest.getSurname());
        specificUser.setAge(registerRequest.getAge());
        specificUser.setGenderType(registerRequest.getGenderType());
        specificUser.setUpdateDate(LocalDateTime.now());
        specificUser.setPhoneNumber(registerRequest.getPhoneNumber());
        specificUser.setUserType(registerRequest.getUserType());

        return specificUser;
    }

    public User convert(RegisterRequest registerRequest) {
        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setUsername(registerRequest.getUsername());
        user.setCreateDate(LocalDateTime.now());
        user.setGenderType(registerRequest.getGenderType());
        user.setAge(registerRequest.getAge());
        user.setSurname(registerRequest.getSurname());
        user.setUserType(registerRequest.getUserType());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        return user;
    }

    public UpdateResponse convert(String updateMessage, RegisterRequest registerRequest){
        UpdateResponse updateResponse = new UpdateResponse();

        updateResponse.setUsername(registerRequest.getUsername());
        updateResponse.setEmail(registerRequest.getEmail());
        updateResponse.setName(registerRequest.getName());
        updateResponse.setSurname(registerRequest.getSurname());
        updateResponse.setAge(registerRequest.getAge());
        updateResponse.setGenderType(registerRequest.getGenderType());
        updateResponse.setUserType(registerRequest.getUserType());
        updateResponse.setUpdateMessage(updateMessage);
        updateResponse.setPhoneNumber(registerRequest.getPhoneNumber());

        return updateResponse;
    }

    public List<UserResponse> convert(List<User> userList) {
        List<UserResponse> userResponse = new ArrayList<>();

        for (User user : userList)
            userResponse.add(convert(user));

        return userList.stream().map(this::convert).toList();
    }
}
