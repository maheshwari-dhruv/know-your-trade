package org.blog.knowyourtrade.repository;

import org.blog.knowyourtrade.dao.entity.Category;
import org.blog.knowyourtrade.dao.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}