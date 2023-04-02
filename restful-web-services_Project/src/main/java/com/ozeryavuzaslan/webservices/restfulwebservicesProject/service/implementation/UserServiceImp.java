package com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.implementation;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.converter.UserConverter;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.UserRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.UserResponse;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.repository.UserRepository;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.UserFinder;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final UserFinder userFinder;

    @Override
    public List<UserResponse> findAll() {
        return userConverter.convert(userRepository.findAll());
    }

    @Override
    public UserResponse save(UserRequest userRequest) {
        return userConverter
                .convert(userRepository
                        .save(userConverter
                                .convert(userRequest)));
    }

    @Override
    public UserResponse findOne(int id) {
        return userConverter.convert(userFinder.findSpecificUser(id));
    }

    @Override
    public UserResponse findOneByEmail(String eMail) {
        return userConverter.convert(userFinder.findSpecificUser(eMail));
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(userFinder.findSpecificUser(id).getId());
    }

    @Override
    public UserResponse updateByEmail(UserRequest userRequest) {
        return userConverter
                .convert(userRepository
                        .save(userConverter
                                .convert(userRequest,
                                        userFinder
                                                .findSpecificUser(userRequest
                                                        .getEmail()))));
    }

    @Override
    public UserResponse updateById(int id, UserRequest userRequest) {
        return userConverter
                .convert(userRepository
                        .save(userConverter
                                .convert(userRequest,
                                        userFinder.findSpecificUser(id))));
    }
}
