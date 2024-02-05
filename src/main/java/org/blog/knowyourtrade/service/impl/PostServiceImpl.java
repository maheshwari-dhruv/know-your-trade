package org.blog.knowyourtrade.service.impl;

import org.blog.knowyourtrade.repository.PostRepository;
import org.blog.knowyourtrade.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
}
