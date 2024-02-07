package org.blog.knowyourtrade.repository;

import org.blog.knowyourtrade.dao.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findAllByCategory(String category);
}
