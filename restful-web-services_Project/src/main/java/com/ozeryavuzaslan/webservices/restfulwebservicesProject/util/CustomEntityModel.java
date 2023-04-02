package com.ozeryavuzaslan.webservices.restfulwebservicesProject.util;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.PostResponse;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.UserResponse;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.PostService;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomEntityModel {
    private final UserService userService;
    private final PostService postService;

    public EntityModel<UserResponse> getEntityModelForUsers(int id, String rel, WebMvcLinkBuilder link) {
        EntityModel<UserResponse> entityModel = EntityModel.of(userService.findOne(id));
        entityModel.add(link.withRel(rel));
        return entityModel;
    }

    public EntityModel<UserResponse> getEntityModelForUsers(String eMail, String rel, WebMvcLinkBuilder link) {
        EntityModel<UserResponse> entityModel = EntityModel.of(userService.findOneByEmail(eMail));
        entityModel.add(link.withRel(rel));
        return entityModel;
    }

    public EntityModel<PostResponse> getEntityModelForPost(int id, String rel, WebMvcLinkBuilder link) {
        EntityModel<PostResponse> entityModel = EntityModel.of(postService.findOne(id));
        entityModel.add(link.withRel(rel));
        return entityModel;
    }
}