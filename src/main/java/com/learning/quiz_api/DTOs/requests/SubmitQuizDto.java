package com.learning.quiz_api.DTOs.requests;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class SubmitQuizDto {
    private Map<Long, Set<Integer>> questionAnswers;
}