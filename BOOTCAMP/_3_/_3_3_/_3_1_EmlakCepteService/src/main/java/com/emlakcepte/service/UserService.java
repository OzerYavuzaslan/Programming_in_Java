package com.emlakcepte.service;

import com.emlakcepte.repository.*;
import com.emlakcepte.model.Search;
import com.emlakcepte.model.User;

import java.util.List;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public void createUser(User user) {
        System.out.println("ben bir userDao objesiyim:" + userRepository.toString());

        if (user.getPassword().length() < 8) {
            System.out.println("Şifre en az 8 karakterli olmalı");
            return;
        }

        userRepository.createUser(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAllUsers();
    }

    public void printAllUser() {
        getAllUser().forEach(user -> System.out.println(user.getName()));
    }

    public void saveSearch(User user, Search search){
        userRepository.saveSearch(search, user);
    }
}