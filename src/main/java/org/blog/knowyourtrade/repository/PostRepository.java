package org.blog.knowyourtrade.repository;

import jakarta.transaction.Transactional;
import org.blog.knowyourtrade.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findAllByCategory(String category);

    @Modifying
    @Transactional
    @Query("UPDATE Post p " +
            "SET p.postTitle = :newTitle, " +
            "p.postContent = :newContent, " +
            "p.category = :newCategory, " +
            "p.updatedAt = :newUpdatedAt " +
            "WHERE p.postId = :postId")
    int updatePost(
            @Param("newTitle") String newTitle,
            @Param("newContent") String newContent,
            @Param("newCategory") String newCategory,
            @Param("newUpdatedAt") LocalDateTime newUpdatedAt,
            @Param("postId") String postId);
}
