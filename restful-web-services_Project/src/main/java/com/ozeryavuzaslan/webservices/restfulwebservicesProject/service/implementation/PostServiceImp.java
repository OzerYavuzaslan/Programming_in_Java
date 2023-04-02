package com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.implementation;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.converter.PostConverter;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.PostRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.PostResponse;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.repository.PostRepository;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.PostFinder;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.PostService;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.UserFinder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final UserFinder userFinder;
    private final PostFinder postFinder;

    @Override
    public PostResponse savePost(int userID, PostRequest postRequest) {
        return postConverter
                .convert(postRepository
                        .save(postConverter
                                .convert(postRequest,
                                        userFinder.findSpecificUser(userID))));
    }

    @Override
    public PostResponse savePost(String email, PostRequest postRequest) {
        return postConverter
                .convert(postRepository
                        .save(postConverter
                                .convert(postRequest,
                                        userFinder.findSpecificUser(email))));
    }

    @Override
    public List<PostResponse> findAll() {
        return postConverter.convert(postRepository.findAll());
    }

    @Override
    public PostResponse findOne(int id) {
        return postConverter.convert(postFinder.findSpecificPost(id));
    }

    @Override
    public List<PostResponse> findOne(String title) {
        return postConverter.convert(postFinder.findSpecificPost(title));
    }

    @Override
    public List<PostResponse> findAuthorsPosts(String eMail) {
        return postConverter
                .convert(postFinder
                        .findUsersPosts(userFinder
                                .findSpecificUser(eMail).getId()));
    }

    @Override
    public List<PostResponse> findAuthorsPosts(int userID) {
        return postConverter.convert(postFinder.findUsersPosts(userID));
    }

    @Override
    public PostResponse updatePost(int id, PostRequest postRequest) {
        return postConverter.convert(postRepository
                .save(postConverter
                        .convert(postRequest,
                                postFinder.findSpecificPost(id))));
    }

    @Override
    public void deleteById(int id){
        postRepository.deleteById(postFinder.findSpecificPost(id).getId());
    }

    @Override
    @Transactional
    public void deleteByUserId(int userID){
        postRepository.deleteByUser_id(userFinder.findSpecificUser(userID).getId());
    }
}
