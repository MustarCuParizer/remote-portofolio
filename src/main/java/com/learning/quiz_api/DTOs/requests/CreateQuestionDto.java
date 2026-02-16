package com.learning.quiz_api.DTOs.requests;

import com.learning.quiz_api.entities.Question.QuestionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateQuestionDto {
    private Long categoryId;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Question type cannot be null")
    private QuestionType questionType;

    @NotEmpty(message = "Options cannot be empty")
    private Set<String> options;

    @NotEmpty(message = "Correct options cannot be empty")
    private Set<Integer> correctOptions;

    private String explanation;
}
