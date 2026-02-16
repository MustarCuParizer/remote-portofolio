package com.learning.quiz_api.services;

import com.learning.quiz_api.DTOs.responses.CategoryResponseDto;
import com.learning.quiz_api.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name);
    List<CategoryResponseDto> getAllCategories();
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
}