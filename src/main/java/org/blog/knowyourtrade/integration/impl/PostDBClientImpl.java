package org.blog.knowyourtrade.integration.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.dao.entity.Post;
import org.blog.knowyourtrade.domain.enums.ErrorCode;
import org.blog.knowyourtrade.domain.exception.ServiceException;
import org.blog.knowyourtrade.integration.PostDBClient;
import org.blog.knowyourtrade.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class PostDBClientImpl implements PostDBClient {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPostsFromDB() {
        try {
            List<Post> allPosts = postRepository.findAll();

            if (allPosts.isEmpty()) {
                return new ArrayList<>();
            }

            return allPosts;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Post getIndividualPostById(String postId) {
        try {
            Optional<Post> postResult = postRepository.findById(postId);

            if (!postResult.isPresent()) {
                // TODO: Update this exception. Add new error code
                throw new ServiceException("No Post found", ErrorCode.UNKNOWN_EXCEPTION);
            }

            return postResult.get();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Post> getAllPostsByCategory(String category) {
        try {
            List<Post> categoryResult = postRepository.findAllByCategory(category);

            if (categoryResult.isEmpty()) {
                return new ArrayList<>();
            }

            return categoryResult;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
