package com.ozeryavuzaslan.webservices.restfulwebservicesProject.controller;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.UserRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.UserResponse;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.UserService;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.CustomEntityModel;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.CustomLocation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.Constants.*;

@Tag(
        name = "CRUD REST APIs for User Resource",
        description = "..."
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CustomEntityModel customEntityModel;
    private final CustomLocation customLocation;

    @Operation(
            summary = "Create User REST API",
            description = "Create User REST API is used to save user in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity
                .created(customLocation
                        .getURILocation(USER_EMAIL_PATH,
                                userService
                                        .save(userRequest)
                                            .getEmail())).build();
    }

    @PutMapping("/updateByEmail")
    public ResponseEntity<EntityModel<UserResponse>> updateUser(@RequestParam String email, @Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(customEntityModel
                .getEntityModelForUsers(userRequest.getEmail(),
                        SPECIFIC_USER,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(this.getClass())
                                .retrieveUserByEmail(userService
                                        .updateByEmail(userRequest,
                                                email)
                                        .getEmail()))));
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<UserResponse>> updateUser(@PathVariable int id, @Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(customEntityModel
                .getEntityModelForUsers(userRequest.getEmail(),
                        SPECIFIC_USER,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(this.getClass())
                                .retrieveUserByEmail(userService
                                        .updateById(id, userRequest)
                                        .getEmail()))));
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> retrieveAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(
            summary = "Get User By ID REST API",
            description = "Get User By ID REST API is used to get a single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponse>> retrieveUser(@PathVariable int id) {
        return ResponseEntity.ok(customEntityModel
                .getEntityModelForUsers(id,
                        ALL_USERS,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers())));
    }

    @GetMapping("/getuserbyemail/{eMail}")
    public ResponseEntity<UserResponse> retrieveUserByEmail(@PathVariable String eMail){
        return ResponseEntity.ok(userService.findOneByEmail(eMail));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteById(id);
    }

}
