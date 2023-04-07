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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final UserFinder userFinder;
    private final PostFinder postFinder;
    private final ModelMapper modelMapper;

    @Override
    public PostResponse savePost(int userID, PostRequest postRequest) {
        return modelMapper
                .map(postRepository
                        .save(postRepository
                                .save(postConverter
                                        .convert(postRequest,
                                                userFinder
                                                        .findSpecificUser(userID)))),
                        PostResponse.class);
    }

    @Override
    public PostResponse savePost(String email, PostRequest postRequest) {
        return modelMapper
                .map(postRepository
                        .save(postConverter
                                .convert(postRequest,
                                        userFinder
                                                .findSpecificUser(email))),
                        PostResponse.class);
    }

    @Override
    public List<PostResponse> findAll() {
        return postRepository
                .findAll()
                .stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .toList();
    }

    @Override
    public PostResponse findOne(int id) {
        return modelMapper.map(postFinder.findSpecificPost(id), PostResponse.class);
    }

    @Override
    public List<PostResponse> findOne(String title) {
        return postFinder
                .findSpecificPost(title)
                .stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .toList();
    }

    @Override
    public List<PostResponse> findAuthorsPosts(String eMail) {
        return postFinder
                .findUsersPosts(userFinder
                        .findSpecificUser(eMail)
                        .getId())
                .stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .toList();
    }

    @Override
    public List<PostResponse> findAuthorsPosts(int userID) {
        return postFinder
                .findUsersPosts(userID)
                .stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .toList();
    }

    @Override
    public PostResponse updatePost(int id, PostRequest postRequest) {
        return modelMapper
                .map(postRepository
                        .save(postConverter
                                .convert(postRequest,
                                        postFinder
                                                .findSpecificPost(id))),
                        PostResponse.class);
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
