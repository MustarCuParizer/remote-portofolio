package com.learning.quiz_api.DTOs.responses;

import lombok.Data;

import java.util.Set;

@Data
public class QuizResponseDto {
    private Long id;
    private Long userId;
    private boolean finished;
    private Integer correctAnswers;
    private Set<QuestionResponseDto> questions;
}
