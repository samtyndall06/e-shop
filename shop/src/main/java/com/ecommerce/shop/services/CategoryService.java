package com.ecommerce.shop.services;

import com.ecommerce.shop.models.Category;
import com.ecommerce.shop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    // @Autowired tells Spring to automatically inject
    // the CategoryRepository — you don't create it manually
    @Autowired
    private CategoryRepository categoryRepository;

    // Get all categories (used to populate nav menu)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get one category by its ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Get one category by its name
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    // Save a new category to the database
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Delete a category by ID
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}