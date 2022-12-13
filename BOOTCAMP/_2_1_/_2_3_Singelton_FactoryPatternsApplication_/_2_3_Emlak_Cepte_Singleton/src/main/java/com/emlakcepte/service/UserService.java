package com.emlakcepte.service;

import com.emlakcepte.dao.UserDao;
import com.emlakcepte.model.Search;
import com.emlakcepte.model.User;

import java.util.List;

public class UserService {
    private static UserService userService = null; //lazy method
    private final UserDao userDao = new UserDao();

    public static UserService getInstance(){
        if (userService == null)
            userService = new UserService();

        return userService;
    }

    public void createUser(User user) {
        System.out.println("ben bir userDao objesiyim:" + userDao.toString());

        if (user.getPassword().length() < 8) {
            System.out.println("Şifre en az 8 karakterli olmalı");
            return;
        }

        userDao.createUser(user);
    }

    public List<User> getAllUser() {
        return userDao.findAllUsers();
    }

    public void printAllUser() {
        getAllUser().forEach(user -> System.out.println(user.getName()));
    }

    public void saveSearch(User user, Search search){
        userDao.saveSearch(search, user);
    }
}