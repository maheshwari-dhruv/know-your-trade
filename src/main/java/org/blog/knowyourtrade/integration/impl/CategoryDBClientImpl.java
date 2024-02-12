package org.blog.knowyourtrade.integration.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.dao.entity.Category;
import org.blog.knowyourtrade.domain.dto.request.CategoryRequest;
import org.blog.knowyourtrade.domain.dto.response.BlogResponse;
import org.blog.knowyourtrade.domain.enums.ErrorCode;
import org.blog.knowyourtrade.domain.exception.ServiceException;
import org.blog.knowyourtrade.integration.CategoryDBClient;
import org.blog.knowyourtrade.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class CategoryDBClientImpl implements CategoryDBClient {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        try {
            List<Category> allCategory = categoryRepository.findAll();
            log.debug("allCategory size: {}", allCategory.size());

            if (allCategory.isEmpty()) {
                log.debug("No Category found in DB");
                return new ArrayList<>();
            }

            return allCategory;
        } catch (Exception e) {
            log.error("Error fetching categories from db (getAllCategory): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Category addCategoryRecord(CategoryRequest categoryRequest) {
        try {
            Category savedCategory = categoryRepository.save(mapRequestToDAO(categoryRequest));

            log.info("Post Saved with ID: {}", savedCategory.getCategoryId());
            log.debug("SavedPost: {}", savedCategory);
            return savedCategory;
        } catch (Exception e) {
            log.error("Error saving category in db (addCategoryRecord): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BlogResponse deleteCategoryFromDB(String categoryId) {
        try {
            List<Category> categoryFound = categoryRepository.findById(categoryId).stream().toList();

            if (categoryFound.isEmpty()) {
                log.warn("No Category found by ID: {}", categoryId);
                return BlogResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message("No Category found by ID: {}" + categoryId)
                        .status("NOT FOUND")
                        .build();
            }

            log.debug("Category Found by ID: {}", categoryId);
            categoryRepository.deleteById(categoryId);

            return BlogResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Successfully deleted record from db")
                    .status("SUCCESS")
                    .build();
        } catch (Exception e) {
            log.error("Error fetching category from db (deleteCategoryFromDB): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BlogResponse updateCategoryInDB(String categoryId, CategoryRequest categoryRequest) {
        try {
            int categoryResult = categoryRepository.updateCategory(categoryRequest.getCategory(), LocalDateTime.now(), categoryId);

            if (categoryResult == 0) {
                log.debug("No Category updated for ID: {}", categoryId);
                return BlogResponse.builder()
                        .code(categoryResult)
                        .message("No Post updated for ID: " + categoryId)
                        .status("SUCCESS")
                        .build();
            }

            log.debug("Category Updated for ID: {}", categoryId);
            return BlogResponse.builder()
                    .code(categoryResult)
                    .message("Post Updated for ID: " + categoryId)
                    .status("SUCCESS")
                    .build();
        } catch (Exception e) {
            log.error("Error fetching category from db (updateCategoryInDB): {}", e.getMessage());
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Category mapRequestToDAO(CategoryRequest categoryRequest) {
        return Category.builder()
                .categoryId(String.valueOf(UUID.randomUUID()))
                .categoryName(categoryRequest.getCategory())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
