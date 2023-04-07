package com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.implementation;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.converter.UserConverter;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.UserRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.UserResponse;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.User;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.repository.UserRepository;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.UserFinder;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final UserFinder userFinder;
    private final ModelMapper modelMapper;

    @Override
    public List<UserResponse> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
    }

    @Override
    public UserResponse save(UserRequest userRequest) {
        return modelMapper
                .map(userRepository
                        .save(modelMapper
                                .map(userRequest, User.class)),
                        UserResponse.class);
    }

    @Override
    public UserResponse findOne(int id) {
        return modelMapper.map(userFinder.findSpecificUser(id), UserResponse.class);
    }

    @Override
    public UserResponse findOneByEmail(String eMail) {
        return modelMapper.map(userFinder.findSpecificUser(eMail), UserResponse.class);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(userFinder.findSpecificUser(id).getId());
    }

    @Override
    public UserResponse updateByEmail(UserRequest userRequest, String eMail) {
        return modelMapper
                .map(userRepository
                        .save(userConverter
                                .convert(userRequest,
                                        userFinder
                                                .findSpecificUser(eMail))),
                        UserResponse.class);
    }

    @Override
    public UserResponse updateById(int id, UserRequest userRequest) {
        return modelMapper
                .map(userRepository
                        .save(userConverter
                                .convert(userRequest,
                                        userFinder
                                                .findSpecificUser(id))),
                        UserResponse.class);
    }
}
