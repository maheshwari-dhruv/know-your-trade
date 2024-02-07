package org.blog.knowyourtrade.integration;

import org.blog.knowyourtrade.dao.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;

import java.util.List;

public interface PostDBClient {
    List<Post> getAllPostsFromDB();

    Post getIndividualPostById(String postId);

    List<Post> getAllPostsByCategory(String category);

    Post addPostRecord(PostRequest postRequest);
}
