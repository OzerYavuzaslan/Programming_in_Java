package com.emlakcepte.interfaces;

import com.emlakcepte.model.Search;
import com.emlakcepte.model.User;

import java.util.List;

public interface IUserService {
    void createUser(User user);
    List<User> getAllUser();
    void printAllUser();
    void saveSearch(User user, Search search);
}
