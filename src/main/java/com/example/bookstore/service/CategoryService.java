package com.example.bookstore.service;

import com.example.bookstore.exception.CategoryNotFoundException;
import com.example.bookstore.model.Category;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    public List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }

    public Category getCategoryByName(String name) {
        return Arrays.stream(Category.values())
                .filter(category -> category.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with name " + name));
    }

    public List<Category> getCategoriesByType(String type) {
        try {
            return Arrays.stream(Category.values())
                    .filter(category -> category.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException ex) {
            throw new CategoryNotFoundException("Invalid category type: " + type);
        }
    }
}

