package org.blog.knowyourtrade.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.dao.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.domain.dto.response.BlogResponse;
import org.blog.knowyourtrade.integration.PostDBClient;
import org.blog.knowyourtrade.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDBClient postDBClient;

    @Override
    public List<Post> fetchAllPostsFromDB() {
        log.info("Fetch All Posts From DB");
        return postDBClient.getAllPostsFromDB();
    }

    @Override
    public List<Post> fetchIndividualPostFromDB(String postId) {
        log.info("Fetch Individual Post From DB By ID");
        log.debug("ID received: {}", postId);
        return postDBClient.getIndividualPostById(postId);
    }

    @Override
    public List<Post> fetchAllPostsBasedOnCategoryFromDB(String category) {
        log.info("Fetch All Post From DB By Category");
        log.debug("Category received: {}", category.toLowerCase());
        return postDBClient.getAllPostsByCategory(category.toLowerCase());
    }

    @Override
    public Post insertPostRecordInDB(PostRequest postRequest) {
        log.info("Insert Post Into DB");
        log.debug("Request received: {}", postRequest);
        return postDBClient.addPostRecord(postRequest);
    }

    @Override
    public BlogResponse deletePostByIDFromDB(String postId) {
        log.info("Delete Post From DB By ID");
        log.debug("ID received: {}", postId);
        return postDBClient.deleteRecordFromDB(postId);
    }

    @Override
    public BlogResponse updatePostRecordInDB(String postId, PostRequest postRequest) {
        log.info("Update Post in DB By ID");
        log.debug("ID received: {}", postId);
        log.debug("Updated Post Request received: {}", postRequest);
        return postDBClient.updatePostInDB(postId, postRequest);
    }
}
