package com.learning.quiz_api.utils;

import com.learning.quiz_api.DTOs.responses.CategoryResponseDto;
import com.learning.quiz_api.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponseDto toCategoryResponseDto(Category category){
        return new CategoryResponseDto(category.getId(), category.getName());
    }
}
