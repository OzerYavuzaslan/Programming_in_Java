package com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.implementation;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.exception.UserNotFoundException;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.User;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.repository.UserRepository;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.Constants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserFinderImp implements UserFinder {
    private final UserRepository userRepository;

    @Override
    public User findSpecificUser(int id){
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + " -->" + USER_NOT_FOUND));
    }

    @Override
    public User findSpecificUser(String eMail){
        return userRepository
                .findByEmail(eMail)
                .orElseThrow(() -> new UserNotFoundException(eMail + " --> " + USER_NOT_FOUND));
    }
}