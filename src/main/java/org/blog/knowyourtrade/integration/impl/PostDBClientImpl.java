package org.blog.knowyourtrade.integration.impl;

import lombok.extern.slf4j.Slf4j;
//import org.blog.knowyourtrade.aop.Monitor;
import org.blog.knowyourtrade.dao.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.domain.dto.response.BlogResponse;
import org.blog.knowyourtrade.domain.enums.ErrorCode;
//import org.blog.knowyourtrade.domain.enums.MonitorType;
import org.blog.knowyourtrade.domain.exception.ServiceException;
import org.blog.knowyourtrade.integration.PostDBClient;
import org.blog.knowyourtrade.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
//@Monitor(monitor = MonitorType.DATABASE)
public class PostDBClientImpl implements PostDBClient {

    @Autowired
    private PostRepository postRepository;

    @Override
//    @Monitor("getAllPostsFromDB")
    public List<Post> getAllPostsFromDB() {
        try {
            List<Post> allPosts = postRepository.findAll();
            log.debug("All Posts size: {}", allPosts.size());

            if (allPosts.isEmpty()) {
                log.debug("No Post found in DB");
                return new ArrayList<>();
            }

            return allPosts;
        } catch (Exception e) {
            log.error("Error fetching posts from db (getAllPostsFromDB): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
//    @Monitor("getIndividualPostById")
    public List<Post> getIndividualPostById(String postId) {
        try {
            List<Post> postResult = postRepository.findById(postId).stream().toList();

            if (postResult.isEmpty()) {
                log.debug("No Post found by ID: {}", postId);
                return new ArrayList<>();
            }

            log.debug("Post Found by ID: {}", postId);
            return postResult;
        } catch (Exception e) {
            log.error("Error fetching posts from db (getIndividualPostById): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
//    @Monitor("getAllPostsByCategory")
    public List<Post> getAllPostsByCategory(String category) {
        try {
            List<Post> categoryResult = postRepository.findAllByCategory(category);

            if (categoryResult.isEmpty()) {
                log.debug("No Posts found by Category: {}", category);
                return new ArrayList<>();
            }

            log.debug("Total Posts Found by Category: {}", categoryResult.size());
            return categoryResult;
        } catch (Exception e) {
            log.error("Error fetching posts from db (getAllPostsByCategory): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
//    @Monitor("addPostRecord")
    public Post addPostRecord(PostRequest postRequest) {
        try {
            Post savedPost = postRepository.save(mapRequestToDAO(postRequest));

            log.info("Post Saved with ID: {}", savedPost.getPostId());
            log.debug("SavedPost: {}", savedPost);
            return savedPost;
        } catch (Exception e) {
            log.error("Error saving posts in db (addPostRecord): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BlogResponse deleteRecordFromDB(String postId) {
        try {
            postRepository.deleteById(postId);

            log.debug("Post Found by ID: {}", postId);
            return BlogResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Successfully deleted record from db")
                    .status("SUCCESS")
                    .build();
        } catch (Exception e) {
            log.error("Error fetching posts from db (deleteRecordFromDB): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BlogResponse updatePostInDB(String postId, PostRequest postRequest) {
        try {
            int postResult = postRepository.updatePost(postRequest.getTitle(), postRequest.getContent(), postRequest.getCategory(), LocalDateTime.now(), postId);

            if (postResult == 0) {
                log.debug("No Post updated for ID: {}", postId);
                return BlogResponse.builder()
                        .code(postResult)
                        .message("No Post updated for ID: " + postId)
                        .status("SUCCESS")
                        .build();
            }

            log.debug("Post Updated for ID: {}", postId);
            return BlogResponse.builder()
                    .code(postResult)
                    .message("Post Updated for ID: " + postId)
                    .status("SUCCESS")
                    .build();
        } catch (Exception e) {
            log.error("Error fetching posts from db (updatePostInDB): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Post mapRequestToDAO(PostRequest postRequest) {
        return Post.builder()
                .postId(String.valueOf(UUID.randomUUID()))
                .postTitle(postRequest.getTitle())
                .postContent(postRequest.getContent())
                .category(postRequest.getCategory())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
