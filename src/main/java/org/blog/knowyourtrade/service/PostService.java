package org.blog.knowyourtrade.service;

import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.domain.dto.response.BlogResponse;

public interface PostService {
    BlogResponse fetchAllPostsFromDB();

    BlogResponse fetchIndividualPostFromDB(String postId);

    BlogResponse insertPostRecordInDB(PostRequest postRequest);

    BlogResponse deletePostByIDFromDB(String postId);

    BlogResponse updatePostRecordInDB(String postId, PostRequest postRequest);
}
