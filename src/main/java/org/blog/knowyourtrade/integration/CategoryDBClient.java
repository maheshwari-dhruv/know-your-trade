package org.blog.knowyourtrade.integration;

import org.blog.knowyourtrade.dao.entity.Category;

import java.util.List;

public interface CategoryDBClient {
    List<Category> getAllCategory();
}
