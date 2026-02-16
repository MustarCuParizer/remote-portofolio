package com.learning.quiz_api.services;

import com.learning.quiz_api.DTOs.requests.CreateQuizDto;
import com.learning.quiz_api.DTOs.requests.SubmitQuizDto;
import com.learning.quiz_api.DTOs.responses.QuizResponseDto;
import com.learning.quiz_api.DTOs.responses.QuizResultDto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizService {
    QuizResponseDto createQuiz(CreateQuizDto createQuizDto, Long userId);
    QuizResponseDto findById(Long id);
    QuizResponseDto findByIdForUser(Long id, Long userId);
    List<QuizResponseDto> findAll();
    void delete(Long id);
    QuizResultDto submitQuiz(Long quizId, SubmitQuizDto submitQuizDto, Long userId);
}
