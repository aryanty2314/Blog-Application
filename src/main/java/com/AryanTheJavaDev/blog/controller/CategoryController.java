package com.AryanTheJavaDev.blog.controller;

import com.AryanTheJavaDev.blog.dto_s.CategoryDTO;
import com.AryanTheJavaDev.blog.dto_s.CreateCategoryRequest;
import com.AryanTheJavaDev.blog.entities.Category;
import com.AryanTheJavaDev.blog.mappers.CategoryMapper;
import com.AryanTheJavaDev.blog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")

public class CategoryController
{
    private CategoryMapper categoryMapper;

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryMapper categoryMapper, CategoryService categoryService) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }



    @GetMapping
    public ResponseEntity<List<CategoryDTO>> listCategories()
    {
        List<CategoryDTO> categories = categoryService.listOfCategory()
                .stream().map(categoryMapper :: todto)
                .toList();
        return categories.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest)
    {
      Category categoryToCreate = categoryMapper.toEntity(createCategoryRequest);
       Category savedcategory = categoryService.createCategory(categoryToCreate);
      return new ResponseEntity<>(categoryMapper.todto(savedcategory), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id)
    {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
