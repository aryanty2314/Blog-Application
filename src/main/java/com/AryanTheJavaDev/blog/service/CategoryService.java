package com.AryanTheJavaDev.blog.service;

import com.AryanTheJavaDev.blog.entities.Category;

import java.util.List;
import java.util.UUID;


public interface CategoryService
{
 List<Category> listOfCategory();

 Category createCategory(Category category);

  void deleteCategory(UUID id);

  Category getCategory(UUID id);
}
