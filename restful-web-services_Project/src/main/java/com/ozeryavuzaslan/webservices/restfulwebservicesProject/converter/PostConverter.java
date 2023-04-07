package com.ozeryavuzaslan.webservices.restfulwebservicesProject.converter;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.PostRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.Post;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostConverter {
    public Post convert(PostRequest postRequest, User user){
        Post post = new Post();

        post.setUser(user);
        post.setPost(postRequest.getPost());
        post.setTitle(postRequest.getTitle());
        post.setAdddate(LocalDateTime.now());
        post.setUpddate(LocalDateTime.now());

        return post;
    }

    public Post convert(PostRequest postRequest, Post post){
        post.setPost(postRequest.getPost());
        post.setTitle(postRequest.getTitle());
        post.setUpddate(LocalDateTime.now());

        return post;
    }
}
