package com.learning.quiz_api.DTOs.requests;

import com.learning.quiz_api.entities.Question.QuestionType;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateQuestionDto {
    private Long categoryId;
    private String description;
    private QuestionType questionType;
    private Set<String> options;
    private Set<Integer> correctOptions;
    private String explanation;
}
