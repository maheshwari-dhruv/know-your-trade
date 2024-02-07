package org.blog.knowyourtrade.service;

import org.blog.knowyourtrade.dao.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;

import java.util.List;

public interface PostService {
    List<Post> fetchAllPostsFromDB();

    Post fetchIndividualPostFromDB(String postId);

    List<Post> fetchAllPostsBasedOnCategoryFromDB(String category);

    Post insertPostRecordInDB(PostRequest postRequest);
}
