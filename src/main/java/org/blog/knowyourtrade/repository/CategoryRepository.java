package org.blog.knowyourtrade.repository;

import jakarta.transaction.Transactional;
import org.blog.knowyourtrade.dao.entity.Category;
import org.blog.knowyourtrade.dao.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Modifying
    @Transactional
    @Query("UPDATE Category c " +
            "SET c.categoryName = :categoryName, " +
            "c.updatedAt = :newUpdatedAt " +
            "WHERE c.categoryId = :categoryId")
    int updateCategory(
            @Param("categoryName") String categoryName,
            @Param("newUpdatedAt") LocalDateTime newUpdatedAt,
            @Param("categoryId") String categoryId);
}