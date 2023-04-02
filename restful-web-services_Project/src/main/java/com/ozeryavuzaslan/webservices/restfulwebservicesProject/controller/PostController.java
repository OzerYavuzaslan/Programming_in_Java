package com.ozeryavuzaslan.webservices.restfulwebservicesProject.controller;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request.PostRequest;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.PostResponse;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.service.PostService;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.CustomEntityModel;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CustomEntityModel customEntityModel;
    private final CustomLocation customLocation;

    @PostMapping("{userid}")
    public ResponseEntity<PostResponse> createPost(@PathVariable int userid, @Valid @RequestBody PostRequest postRequest){
        return ResponseEntity
                .created(customLocation
                        .getURILocation(POST_ORIGIN_PATH, POST_TITLE_PATH,
                                postService.savePost(userid, postRequest).getTitle())).build();
    }

    @PostMapping("/byAuthorEmail/{authorEmail}")
    public ResponseEntity<PostResponse> createPost(@PathVariable String authorEmail, @Valid @RequestBody PostRequest postRequest){
        return ResponseEntity
                .created(customLocation
                        .getURILocation(POST_TITLE_PATH,
                                postService.savePost(authorEmail, postRequest).getTitle())).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> retrieveAllPosts(){
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<PostResponse>> retrievePost(@PathVariable int id){
        return ResponseEntity.ok(customEntityModel
                .getEntityModelForPost(id,
                        ALL_POSTS,
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                        .methodOn(this.getClass()).retrieveAllPosts())));
    }

    @GetMapping("/byTitle/{title}")
    public ResponseEntity<List<PostResponse>> retrievePost(@PathVariable String title){
        return ResponseEntity.ok(postService.findOne(title));
    }

    @GetMapping("/byAuthorEmail/{userEmail}")
    public ResponseEntity<List<PostResponse>> retrieveAuthorsPosts(@PathVariable String userEmail){
        return ResponseEntity.ok(postService.findAuthorsPosts(userEmail));
    }

    @GetMapping("/byAuthorID/{userID}")
    public ResponseEntity<List<PostResponse>> retrieveAuthorsPosts(@PathVariable int userID){
        return ResponseEntity.ok(postService.findAuthorsPosts(userID));
    }

    @PutMapping("{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable int id, @Valid @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.updatePost(id, postRequest));
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable int id){
        postService.deleteById(id);
    }

    @DeleteMapping("/byAuthorId/{userid}")
    public void deletePostByUserID(@PathVariable int userid){
        postService.deleteByUserId(userid);
    }
}
