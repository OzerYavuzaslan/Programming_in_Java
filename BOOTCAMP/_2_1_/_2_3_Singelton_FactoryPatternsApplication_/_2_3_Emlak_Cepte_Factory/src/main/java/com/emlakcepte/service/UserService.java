package com.emlakcepte.service;

import com.emlakcepte.dao.UserDao;
import com.emlakcepte.interfaces.IUserService;
import com.emlakcepte.model.Search;
import com.emlakcepte.model.User;

import java.util.List;

public class UserService implements IUserService {
    private final UserDao userDao = new UserDao();
    @Override
    public void createUser(User user) {
        userDao.createUser(user);
        System.out.println("User has been created.");
    }
    @Override
    public List<User> getAllUser() {
        return userDao.findAllUsers();
    }
    @Override
    public void printAllUser() {
        getAllUser().forEach(user -> System.out.println(user.getName()));
    }
    @Override
    public void saveSearch(User user, Search search){
        userDao.saveSearch(search, user);
    }
}