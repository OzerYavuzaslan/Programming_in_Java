package com.emlakcepte.controller;

import com.emlakcepte.model.Search;
import com.emlakcepte.model.User;
import com.emlakcepte.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAllUser();
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user){
        userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/saveSearch")
    public ResponseEntity<Search> saveSearch(@RequestBody User user, @RequestBody Search search){
        userService.saveSearch(user, search);
        return new ResponseEntity<>(search, HttpStatus.ACCEPTED);
    }
}