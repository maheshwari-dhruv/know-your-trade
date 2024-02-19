package org.blog.knowyourtrade.service;

import org.blog.knowyourtrade.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.domain.dto.response.BlogResponse;

import java.util.List;

public interface PostService {
    BlogResponse fetchAllPostsFromDB();

    BlogResponse fetchIndividualPostFromDB(String postId);

    BlogResponse fetchAllPostsBasedOnCategoryFromDB(String category);

    BlogResponse insertPostRecordInDB(PostRequest postRequest);

    BlogResponse deletePostByIDFromDB(String postId);

    BlogResponse updatePostRecordInDB(String postId, PostRequest postRequest);
}
