package com.learning.quiz_api.DTOs.responses;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class QuizResultDto {
    private Long quizId;
    private int totalQuestions;
    private int correctAnswers;
    private Map<Long, Set<Integer>> userAnswers;
    private Map<Long, Set<Integer>> correctAnswersMap;
}
