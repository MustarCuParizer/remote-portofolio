package com.learning.quiz_api.DTOs.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateQuizDto {
    @Min(value = 1, message = "Number of questions must be at least 1")
    private int numQuestions;

    private Set<Long> categories;
}
