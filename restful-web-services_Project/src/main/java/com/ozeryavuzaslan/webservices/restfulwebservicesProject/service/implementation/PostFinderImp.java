package com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.implementation;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.exception.PostNotFoundException;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.Post;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.repository.PostRepository;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.PostFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.Constants.POST_NOT_FOUND_DEFINITION;

@Service
@RequiredArgsConstructor
public class PostFinderImp implements PostFinder {
    private final PostRepository postRepository;

    @Override
    public Post findSpecificPost(int id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new PostNotFoundException(id + " -->" + POST_NOT_FOUND_DEFINITION));
    }

    @Override
    public List<Post> findSpecificPost(String title) {
        return postRepository
                .findByTitleContainingIgnoreCase(title)
                .orElseThrow(() -> new PostNotFoundException(title + " -->" + POST_NOT_FOUND_DEFINITION));
    }

    @Override
    public List<Post> findUsersPosts(int userID) {
        return postRepository.findByUser_id(userID);
    }
}
