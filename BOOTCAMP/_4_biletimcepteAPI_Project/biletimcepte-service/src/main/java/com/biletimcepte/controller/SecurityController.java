package com.biletimcepte.controller;

import com.biletimcepte.configuration.RabbitMQConfiguration;
import com.biletimcepte.converter.UserConverter;
import com.biletimcepte.dto.request.NotificationRequest;
import com.biletimcepte.dto.request.RegisterRequest;
import com.biletimcepte.dto.response.UserResponse;
import com.biletimcepte.model.Role;
import com.biletimcepte.model.User;
import com.biletimcepte.repository.IRoleRepository;
import com.biletimcepte.service.SecurityUserDetailsService;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

import static com.biletimcepte.util.Constants.*;

@Data
@Controller
public class SecurityController {
    private SecurityUserDetailsService userDetailsManager;
    private PasswordEncoder passwordEncoder;
    private IRoleRepository iRoleRepository;
    private RabbitTemplate rabbitTemplate;
    private UserConverter userConverter;

    @Autowired
    public SecurityController(SecurityUserDetailsService userDetailsManager, PasswordEncoder passwordEncoder,
                              IRoleRepository iRoleRepository, RabbitTemplate rabbitTemplate,
                              UserConverter userConverter) {
       setIRoleRepository(iRoleRepository);
       setPasswordEncoder(passwordEncoder);
       setRabbitTemplate(rabbitTemplate);
       setUserDetailsManager(userDetailsManager);
       setUserConverter(userConverter);
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        session.setAttribute(
                "error", getErrorMessage(request)
        );

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
            MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserResponse> addUser(@RequestBody RegisterRequest registerRequest) throws RoleNotFoundException {
        String hashedPassword = getPasswordEncoder().encode(registerRequest.getPassword());
        Set<Role> roleHashSet = Set.of(getIRoleRepository().findByRoleName("USER").orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND)));
        User user = getUserConverter().convert(registerRequest, hashedPassword, roleHashSet);

        getRabbitTemplate().convertAndSend(RabbitMQConfiguration.getQueueName(),
                new NotificationRequest(REGISTER_SUCCESSFUL + " email address: "
                        + user.getEmail(), "MAIL", user.getEmail()));

        getUserDetailsManager().createUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getErrorMessage(HttpServletRequest request) {
        Exception exception = (Exception) request.getSession().getAttribute(SPRING_SECURITY_LAST_EXCEPTION);
        String error = "";

        if (exception instanceof BadCredentialsException)
            error = BAD_CREDENTIAL;
         else if (exception instanceof LockedException)
            error = exception.getMessage();
         else
            error = BAD_CREDENTIAL;

        return error;
    }
}