package org.blog.knowyourtrade.integration.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.dao.entity.Category;
import org.blog.knowyourtrade.integration.CategoryDBClient;
import org.blog.knowyourtrade.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CategoryDBClientImpl implements CategoryDBClient {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
