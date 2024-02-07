package org.blog.knowyourtrade.web.controller;

import org.blog.knowyourtrade.domain.dto.base.GenericResponse;
import org.blog.knowyourtrade.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 1. GET ALL CATEGORY
    @GetMapping("/all")
    public ResponseEntity<GenericResponse<?>> fetchAllCategories() {
        return new ResponseEntity<>(GenericResponse.success(categoryService.fetchAllCategoriesFromDB()), HttpStatus.OK);
    }
}
