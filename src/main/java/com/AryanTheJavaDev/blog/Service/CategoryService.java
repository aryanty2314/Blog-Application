package com.AryanTheJavaDev.blog.Service;

import com.AryanTheJavaDev.blog.Entities.Category;
import com.AryanTheJavaDev.blog.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface CategoryService
{
 List<Category> listOfCategory();

 Category createCategory(Category category);

  void deleteCategory(UUID id);

  Category getCategory(UUID id);
}
