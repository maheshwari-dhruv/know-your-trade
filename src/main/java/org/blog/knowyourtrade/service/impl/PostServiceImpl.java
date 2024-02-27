package org.blog.knowyourtrade.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.domain.dto.mapper.PostDTO;
import org.blog.knowyourtrade.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.domain.dto.response.BlogResponse;
import org.blog.knowyourtrade.integration.PostDBClient;
import org.blog.knowyourtrade.service.PostService;
import org.blog.knowyourtrade.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDBClient postDBClient;

    @Override
    public BlogResponse fetchAllPostsFromDB() {
        log.info("Fetch All Posts From DB");
        List<Post> allPostsFromDB = postDBClient.getAllPostsFromDB();

        return BlogResponse.builder()
                .postDTO(mappedPostToPostDTO(allPostsFromDB))
                .build();
    }

    @Override
    public BlogResponse fetchIndividualPostFromDB(String postId) {
        log.info("Fetch Individual Post From DB By ID");
        log.debug("ID received: {}", postId);
        List<Post> individualPostById = postDBClient.getIndividualPostById(postId);

        return BlogResponse.builder()
                .postDTO(mappedPostToPostDTO(individualPostById))
                .build();
    }

    @Override
    public BlogResponse insertPostRecordInDB(PostRequest postRequest) {
        log.info("Insert Post Into DB");
        log.debug("Request received: {}", postRequest);
        List<Post> posts = postDBClient.addPostRecord(postRequest);
        return BlogResponse.builder()
                .postDTO(mappedPostToPostDTO(posts))
                .build();
    }

    @Override
    public BlogResponse deletePostByIDFromDB(String postId) {
        log.info("Delete Post From DB By ID");
        log.debug("ID received: {}", postId);
        List<Post> posts = postDBClient.deleteRecordFromDB(postId);
        return BlogResponse.builder()
                .postDTO(mappedPostToPostDTO(posts))
                .build();
    }

    @Override
    public BlogResponse updatePostRecordInDB(String postId, PostRequest postRequest) {
        log.info("Update Post in DB By ID");
        log.debug("ID received: {}", postId);
        log.debug("Updated Post Request received: {}", postRequest);
        List<Post> posts = postDBClient.updatePostInDB(postId, postRequest);
        return BlogResponse.builder()
                .postDTO(mappedPostToPostDTO(posts))
                .build();
    }

    private List<PostDTO> mappedPostToPostDTO(List<Post> allPostsFromDB) {
        List<PostDTO> resultDTO = new ArrayList<>();

        for (Post post: allPostsFromDB) {
            PostDTO postDTO = PostDTO.builder()
                    .postId(post.getPostId())
                    .postTitle(post.getPostTitle())
                    .postContent(post.getPostContent())
                    .category(post.getCategory())
                    .createdAt(DateUtils.formatPostDate(post.getCreatedAt()))
                    .updatedAt(DateUtils.formatPostDate(post.getUpdatedAt()))
                    .build();

            resultDTO.add(postDTO);
        }

        return resultDTO;
    }
}
