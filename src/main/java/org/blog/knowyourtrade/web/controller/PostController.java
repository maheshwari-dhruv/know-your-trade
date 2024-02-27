package org.blog.knowyourtrade.web.controller;

import org.blog.knowyourtrade.domain.dto.base.GenericResponse;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.service.PostService;
import org.blog.knowyourtrade.validation.RequestValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<?>> fetchAllPosts() {
        return new ResponseEntity<>(GenericResponse.success(postService.fetchAllPostsFromDB()), HttpStatus.OK);
    }

    @GetMapping("/individual/{postId}")
    public ResponseEntity<GenericResponse<?>> fetchIndividualPost(@PathVariable("postId") String postId) {
        RequestValidate.validatePostId(postId);
        return new ResponseEntity<>(GenericResponse.success(postService.fetchIndividualPostFromDB(postId)), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<GenericResponse<?>> insertPostRecord(@RequestBody PostRequest postRequest) {
        RequestValidate.validateRequest(postRequest);
        return new ResponseEntity<>(GenericResponse.success(postService.insertPostRecordInDB(postRequest)), HttpStatus.OK);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<GenericResponse<?>> updatePostRecord(@PathVariable("postId") String postId, @RequestBody PostRequest postRequest) {
        RequestValidate.validatePostId(postId);
        RequestValidate.validateRequest(postRequest);
        return new ResponseEntity<>(GenericResponse.success(postService.updatePostRecordInDB(postId, postRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<GenericResponse<?>> deletePostByID(@PathVariable("postId") String postId) {
        RequestValidate.validatePostId(postId);
        return new ResponseEntity<>(GenericResponse.success(postService.deletePostByIDFromDB(postId)), HttpStatus.OK);
    }
}
