package com.biletimcepte.controller;

import com.biletimcepte.dto.request.RegisterRequest;
import com.biletimcepte.dto.response.UpdateResponse;
import com.biletimcepte.dto.response.UserResponse;
import com.biletimcepte.service.IUserService;
import lombok.Data;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Data
@RestController
@RequestMapping("/users")
public class UserController {
    private IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService){
        setIUserService(iUserService);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listOfUsers(){
        return ResponseEntity.ok(getIUserService().listUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) throws InvalidKeySpecException, NoSuchAlgorithmException, PSQLException {
        return ResponseEntity.ok(getIUserService().registerUser(registerRequest));
    }

    @PutMapping
    public ResponseEntity<UpdateResponse> update(@RequestBody RegisterRequest registerRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return ResponseEntity.ok(getIUserService().updateUser(registerRequest));
    }
}
