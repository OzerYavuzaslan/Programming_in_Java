package com.ozeryavuzaslan.webservices.restfulwebservicesProject.service;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.Post;

import java.util.List;

public interface PostFinder {
    Post findSpecificPost(int id);
    List<Post> findSpecificPost(String title);
    List<Post> findUsersPosts(int userID);
}
