package com.learning.quiz_api.DTOs.responses;

import com.learning.quiz_api.entities.Question.QuestionType;
import lombok.Data;

import java.util.Set;

@Data
public class QuestionResponseDto {
    private Long id;
    private String category;
    private String description;
    private QuestionType questionType;
    private Set<String> options;
    private String explanation;
}