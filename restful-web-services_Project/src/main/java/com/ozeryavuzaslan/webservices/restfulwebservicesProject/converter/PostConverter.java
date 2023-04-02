package com.ozeryavuzaslan.webservices.restfulwebservicesProject.converter;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.PostRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.PostResponse;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.Post;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostConverter {
    public PostResponse convert(Post post){
        PostResponse postResponse = new PostResponse();

        postResponse.setAuthor(post.getUser().getName() + " " + post.getUser().getSurname());
        postResponse.setAuthorEmail(post.getUser().getEmail());
        postResponse.setTitle(post.getTitle());
        postResponse.setPost(post.getPost());
        postResponse.setPostAdddate(post.getAdddate());
        postResponse.setPostUpddate(post.getUpddate());

        return postResponse;
    }

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

    public List<PostResponse> convert(List<Post> postList) {
        List<PostResponse> postResponse = new ArrayList<>();

        for (Post post : postList)
            postResponse.add(convert(post));

        return postList.stream().map(this::convert).toList();
    }
}
