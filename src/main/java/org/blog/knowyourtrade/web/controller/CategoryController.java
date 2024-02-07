package org.blog.knowyourtrade.web.controller;

import org.blog.knowyourtrade.domain.dto.base.GenericResponse;
import org.blog.knowyourtrade.domain.dto.request.CategoryRequest;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 2. INSERT RECORDS IN DB
    @PostMapping("/add")
    public ResponseEntity<GenericResponse<?>> insertCategoryRecord(@RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(GenericResponse.success(categoryService.insertCategoryRecordInDB(categoryRequest)), HttpStatus.OK);
    }
}
