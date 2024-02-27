package org.blog.knowyourtrade.integration.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.domain.enums.ErrorCode;
import org.blog.knowyourtrade.domain.exception.ServiceException;
import org.blog.knowyourtrade.integration.PostDBClient;
import org.blog.knowyourtrade.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class PostDBClientImpl implements PostDBClient {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPostsFromDB() {
        Instant startTime = Instant.now();
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
        } finally {
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            log.debug("Time taken to fetch posts: {} milliseconds", duration.toMillis());
        }
    }

    @Override
    public List<Post> getIndividualPostById(String postId) {
        Instant startTime = Instant.now();
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
        } finally {
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            log.debug("Time taken to fetch posts: {} milliseconds", duration.toMillis());
        }
    }

    @Override
    public List<Post> addPostRecord(PostRequest postRequest) {
        Instant startTime = Instant.now();
        try {
            Post savedPost = postRepository.save(mapRequestToDAO(postRequest));

            log.info("Post Saved with ID: {}", savedPost.getPostId());
            log.debug("SavedPost: {}", savedPost);
            return List.of(savedPost);
        } catch (Exception e) {
            log.error("Error saving posts in db (addPostRecord): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        } finally {
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            log.debug("Time taken to fetch posts: {} milliseconds", duration.toMillis());
        }
    }

    @Override
    public List<Post> deleteRecordFromDB(String postId) {
        Instant startTime = Instant.now();
        try {
            List<Post> postFound = postRepository.findById(postId).stream().toList();

            if (postFound.isEmpty()) {
                log.warn("No Post found by ID: {}", postId);
                return new ArrayList<>();
            }

            log.debug("Post Found by ID: {}", postId);
            postRepository.deleteById(postId);
            return postFound;
        } catch (Exception e) {
            log.error("Error fetching posts from db (deleteRecordFromDB): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        } finally {
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            log.debug("Time taken to fetch posts: {} milliseconds", duration.toMillis());
        }
    }

    @Override
    public List<Post> updatePostInDB(String postId, PostRequest postRequest) {
        Instant startTime = Instant.now();
        try {
            List<Post> postFound = postRepository.findById(postId).stream().toList();

            if (postFound.isEmpty()) {
                log.warn("No Post found by ID: {}", postId);
                return new ArrayList<>();
            }

            log.debug("Post Found by ID: {}", postId);
            Post post = mappedToUpdatedDAO(postFound, postRequest);

            int postResult = postRepository.updatePost(post.getPostTitle(), post.getPostContent(), post.getCategory(), post.getUpdatedAt(), post.getPostId());

            if (postResult == 0) {
                log.debug("No Post updated for ID: {}", postId);
                return new ArrayList<>();
            }

            log.debug("Post Updated for ID: {}", postId);
            return List.of(post);
        } catch (Exception e) {
            log.error("Error fetching posts from db (updatePostInDB): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        } finally {
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            log.debug("Time taken to fetch posts: {} milliseconds", duration.toMillis());
        }
    }

    private Post mappedToUpdatedDAO(List<Post> postFound, PostRequest postRequest) {
        return Post.builder()
                .postId(postFound.get(0).getPostId())
                .postTitle(postRequest.getTitle())
                .postContent(postRequest.getContent())
                .category(postRequest.getCategory())
                .createdAt(postFound.get(0).getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private Post mapRequestToDAO(PostRequest postRequest) {
        String content = decodeBase64String(postRequest.getContent());

        return Post.builder()
                .postId(String.valueOf(UUID.randomUUID()))
                .postTitle(postRequest.getTitle())
                .postContent(content)
                .category(postRequest.getCategory())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private String decodeBase64String(String content) {
        byte[] decodedBytes = Base64.getDecoder().decode(content);
        return new String(decodedBytes);
    }
}
