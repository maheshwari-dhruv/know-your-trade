package org.blog.knowyourtrade.integration.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.dao.entity.Category;
import org.blog.knowyourtrade.dao.entity.Post;
import org.blog.knowyourtrade.domain.dto.request.CategoryRequest;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.domain.enums.ErrorCode;
import org.blog.knowyourtrade.domain.exception.ServiceException;
import org.blog.knowyourtrade.integration.CategoryDBClient;
import org.blog.knowyourtrade.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class CategoryDBClientImpl implements CategoryDBClient {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategoryRecord(CategoryRequest categoryRequest) {
        try {
            return categoryRepository.save(mapRequestToDAO(categoryRequest));
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Category mapRequestToDAO(CategoryRequest categoryRequest) {
        return Category.builder()
                .categoryName(categoryRequest.getCategory())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
