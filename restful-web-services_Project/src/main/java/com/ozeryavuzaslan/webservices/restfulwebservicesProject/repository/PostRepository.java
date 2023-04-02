package com.ozeryavuzaslan.webservices.restfulwebservicesProject.repository;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<List<Post>> findByTitleContainingIgnoreCase(String title);
    List<Post> findByUser_id(int userID);
    void deleteByUser_id(int userID);
}
