package org.blog.knowyourtrade.service;

import org.blog.knowyourtrade.dao.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> fetchAllCategoriesFromDB();
}
