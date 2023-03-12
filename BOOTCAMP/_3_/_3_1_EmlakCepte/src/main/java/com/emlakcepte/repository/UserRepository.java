package com.emlakcepte.repository;

import com.emlakcepte.model.Search;
import com.emlakcepte.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final List<User> userList = new ArrayList<>();

    public void createUser(User user) {
        userList.add(user);
    }

    public List<User> findAllUsers() {
        return userList;
    }

    public void saveSearch(Search search, User user){
        user.getSearchList().add(search);
    }
}
