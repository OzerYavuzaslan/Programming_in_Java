package com.biletimcepte.service;

import com.biletimcepte.converter.UserConverter;
import com.biletimcepte.dto.request.NotificationRequest;
import com.biletimcepte.dto.request.RegisterRequest;
import com.biletimcepte.dto.response.UpdateResponse;
import com.biletimcepte.dto.response.UserResponse;
import com.biletimcepte.exception.RoleNotFoundException;
import com.biletimcepte.model.Role;
import com.biletimcepte.model.User;
import com.biletimcepte.repository.IRoleRepository;
import com.biletimcepte.repository.IUserRepository;
import com.biletimcepte.util.LoggerUtilization;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import static com.biletimcepte.util.Constants.*;

@Data
@Service
public class UserService implements IUserService {
    private IUserRepository iUserRepository;
    private UserConverter userConverter;
    private RabbitTemplate rabbitTemplate;
    private IRoleRepository iRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository iUserRepository, UserConverter userConverter, RabbitTemplate rabbitTemplate,
                       IRoleRepository iRoleRepository, PasswordEncoder passwordEncoder) {
        setIUserRepository(iUserRepository);
        setUserConverter(userConverter);
        setRabbitTemplate(rabbitTemplate);
        setIRoleRepository(iRoleRepository);
        setPasswordEncoder(passwordEncoder);
    }

    @Override
    public List<UserResponse> listUsers() {
        return getUserConverter().convert(getIUserRepository().getAllUsers());
    }

    @Override
    public UpdateResponse updateUser(RegisterRequest registerRequest) {
        var isRecordExisted = getIUserRepository().selectByEmail(registerRequest.getEmail());

        String updateMessage;

        if (isRecordExisted.isPresent()) {
            User specificUser = isRecordExisted.get();
            updateMessage = USER_FOUND_AND_UPDATED;
        //    String hashedPassword = PasswordUtil.preparePasswordHash(registerRequest.getPassword(), registerRequest.getEmail());
            String hashedPassword = getPasswordEncoder().encode(registerRequest.getPassword());
            getIUserRepository().save(getUserConverter().convert(specificUser, registerRequest, hashedPassword));
            getRabbitTemplate().convertAndSend("notification", new NotificationRequest("User successfully updated with email: " + registerRequest.getEmail(), "EMAIL", registerRequest.getEmail()));
        }
        else
            updateMessage = USER_NOT_FOUND;

        LoggerUtilization.getLogger().log(Level.INFO, "UserService -> updateUser : " + registerRequest.getEmail());
        return getUserConverter().convert(updateMessage, registerRequest);
    }

    @Override
    public UserResponse registerUser(RegisterRequest registerRequest) {
        User user = getUserConverter().convert(registerRequest);

        Set<Role> roles = new HashSet<>();
        roles.add(getIRoleRepository().findByRoleName("USER").orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND)));
        user.setRoles(roles);
        getIUserRepository().save(user);
        LoggerUtilization.getLogger().log(Level.INFO, "UserService -> registerUser: " + registerRequest.getEmail());
        getRabbitTemplate().convertAndSend("notification", new NotificationRequest(REGISTER_SUCCESSFUL + ": " + registerRequest.getEmail(), "EMAIL", registerRequest.getEmail()));

        return getUserConverter().convert(user);
    }
}
