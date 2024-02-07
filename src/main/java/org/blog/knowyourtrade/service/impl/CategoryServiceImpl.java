package org.blog.knowyourtrade.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.dao.entity.Category;
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
        return categoryDBClient.getAllCategory();
    }
}
