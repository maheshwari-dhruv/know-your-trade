package org.blog.knowyourtrade.integration;

import org.blog.knowyourtrade.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;

import java.util.List;

public interface PostDBClient {
    List<Post> getAllPostsFromDB();

    List<Post> getIndividualPostById(String postId);

    List<Post> getAllPostsByCategory(String category);

    List<Post> addPostRecord(PostRequest postRequest);

    List<Post> deleteRecordFromDB(String postId);

    List<Post> updatePostInDB(String postId, PostRequest postRequest);
}
