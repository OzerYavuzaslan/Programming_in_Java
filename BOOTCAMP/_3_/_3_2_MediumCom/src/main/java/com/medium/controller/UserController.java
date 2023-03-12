package com.medium.controller;

import Model.User;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAllUser();
    }

    @PostMapping
    public User create(User user){
        userService.createUser(user);
        return user;
    }

    @PutMapping
    public User update(String userName, User user){
        User tmpUser = userService.getAllUser().stream().filter(user1 -> user1.getName().equalsIgnoreCase(user.getName())).findFirst().get();
        userService.getAllUser().remove(tmpUser);
        userService.getAllUser().add(user);
        return user;
    }
}
