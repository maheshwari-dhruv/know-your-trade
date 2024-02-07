package org.blog.knowyourtrade.integration;

import org.blog.knowyourtrade.dao.entity.Post;

import java.util.List;

public interface PostDBClient {
    List<Post> getAllPostsFromDB();

    Post getIndividualPostById(String postId);

    List<Post> getAllPostsByCategory(String category);
}
