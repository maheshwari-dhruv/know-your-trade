package org.blog.knowyourtrade.integration;

import org.blog.knowyourtrade.dao.entity.Category;
import org.blog.knowyourtrade.domain.dto.request.CategoryRequest;
import org.blog.knowyourtrade.domain.dto.response.BlogResponse;

import java.util.List;

public interface CategoryDBClient {
    List<Category> getAllCategory();

    Category addCategoryRecord(CategoryRequest categoryRequest);

    BlogResponse deleteCategoryFromDB(String categoryId);

    BlogResponse updateCategoryInDB(String categoryId, CategoryRequest categoryRequest);
}
