package org.blog.knowyourtrade.integration;

import org.blog.knowyourtrade.dao.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.domain.dto.response.BlogResponse;

import java.util.List;

public interface PostDBClient {
    List<Post> getAllPostsFromDB();

    List<Post> getIndividualPostById(String postId);

    List<Post> getAllPostsByCategory(String category);

    Post addPostRecord(PostRequest postRequest);

    BlogResponse deleteRecordFromDB(String postId);

    BlogResponse updatePostInDB(String postId, PostRequest postRequest);
}
