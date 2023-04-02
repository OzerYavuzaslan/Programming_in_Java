package com.ozeryavuzaslan.webservices.restfulwebservicesProject.service;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.PostRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse savePost(int userID, PostRequest postRequest);
    PostResponse savePost(String  email, PostRequest postRequest);
    List<PostResponse> findAll();
    PostResponse findOne(int id);
    List<PostResponse> findOne(String  title);
    List<PostResponse> findAuthorsPosts(String eMail);
    List<PostResponse> findAuthorsPosts(int userID);
    PostResponse updatePost(int id, PostRequest postRequest);
    void deleteById(int id);
    void deleteByUserId(int userID);
}
