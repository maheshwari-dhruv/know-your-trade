package org.blog.knowyourtrade.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.dao.entity.Post;
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
        return postDBClient.getAllPostsFromDB();
    }

    @Override
    public Post fetchIndividualPostFromDB(String postId) {
        return postDBClient.getIndividualPostById(postId);
    }

    @Override
    public List<Post> fetchAllPostsBasedOnCategoryFromDB(String category) {
        return postDBClient.getAllPostsByCategory(category);
    }
}
