package org.blog.knowyourtrade.web.controller;

import org.blog.knowyourtrade.domain.dto.base.GenericResponse;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;

    // 1. GET ALL POSTS
    @GetMapping("/all")
    public ResponseEntity<GenericResponse<?>> fetchAllPosts() {
        return new ResponseEntity<>(GenericResponse.success(postService.fetchAllPostsFromDB()), HttpStatus.OK);
    }

    // 2. GET SINGLE POSTS
    @GetMapping("/{postId}")
    public ResponseEntity<GenericResponse<?>> fetchIndividualPost(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(GenericResponse.success(postService.fetchIndividualPostFromDB(postId)), HttpStatus.OK);
    }

    // 3. GET ALL POSTS BASED ON CATEGORY
    @GetMapping("/all/{category}")
    public ResponseEntity<GenericResponse<?>> fetchAllPostsBasedOnCategory(@PathVariable("category") String category) {
        return new ResponseEntity<>(GenericResponse.success(postService.fetchAllPostsBasedOnCategoryFromDB(category)), HttpStatus.OK);
    }

    // 4. INSERT RECORDS IN DB
    @PostMapping("/add")
    public ResponseEntity<GenericResponse<?>> insertPostRecord(@RequestBody PostRequest postRequest) {
        return new ResponseEntity<>(GenericResponse.success(postService.insertPostRecordInDB(postRequest)), HttpStatus.OK);
    }
}
