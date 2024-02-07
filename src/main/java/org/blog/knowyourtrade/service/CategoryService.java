package org.blog.knowyourtrade.service;

import org.blog.knowyourtrade.dao.entity.Category;
import org.blog.knowyourtrade.domain.dto.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    List<Category> fetchAllCategoriesFromDB();

    Category insertCategoryRecordInDB(CategoryRequest categoryRequest);
}
