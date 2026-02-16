package com.learning.quiz_api.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
}
