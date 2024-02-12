package org.blog.knowyourtrade.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.dao.entity.Category;
import org.blog.knowyourtrade.domain.dto.request.CategoryRequest;
import org.blog.knowyourtrade.domain.dto.response.BlogResponse;
import org.blog.knowyourtrade.integration.CategoryDBClient;
import org.blog.knowyourtrade.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDBClient categoryDBClient;

    @Override
    public List<Category> fetchAllCategoriesFromDB() {
        log.info("Fetch All Category From DB");
        return categoryDBClient.getAllCategory();
    }

    @Override
    public Category insertCategoryRecordInDB(CategoryRequest categoryRequest) {
        log.info("Insert Category Into DB");
        log.debug("Request received: {}", categoryRequest);
        return categoryDBClient.addCategoryRecord(categoryRequest);
    }

    @Override
    public BlogResponse deleteCategoryByIDFromDB(String categoryId) {
        log.info("Delete Category From DB By ID");
        log.debug("ID received: {}", categoryId);
        return categoryDBClient.deleteCategoryFromDB(categoryId);
    }

    @Override
    public BlogResponse updateCategoryRecordInDB(String categoryId, CategoryRequest categoryRequest) {
        log.info("Update Category in DB By ID");
        log.debug("ID received: {}", categoryId);
        log.debug("Updated Category Request received: {}", categoryRequest);
        return categoryDBClient.updateCategoryInDB(categoryId, categoryRequest);
    }
}
