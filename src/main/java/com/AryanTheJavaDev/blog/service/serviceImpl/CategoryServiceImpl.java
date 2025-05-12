package com.AryanTheJavaDev.blog.service.serviceImpl;

import com.AryanTheJavaDev.blog.entities.Category;
import com.AryanTheJavaDev.blog.repository.CategoryRepository;
import com.AryanTheJavaDev.blog.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            if (!category.get().getPosts().isEmpty()) {
                throw new IllegalArgumentException("Category posts associated with it");
            }
            categoryRepository.deleteById(id);
        }

    }

    @Override
    public Category getCategory(UUID id) {

       return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listOfCategory() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName()))
        {
            throw new IllegalArgumentException("Category already exists" + category.getName());
        }
        return categoryRepository.save(category);
    }
}
