package com.learning.quiz_api.services;

import com.learning.quiz_api.DTOs.requests.CreateQuestionDto;
import com.learning.quiz_api.DTOs.requests.UpdateQuestionDto;
import com.learning.quiz_api.DTOs.responses.QuestionResponseDto;
import com.learning.quiz_api.entities.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    QuestionResponseDto save(CreateQuestionDto createQuestionDto);
    List<QuestionResponseDto> findAll();
    QuestionResponseDto findById(Long id);
    QuestionResponseDto update(Long id, UpdateQuestionDto updateQuestionDto);
    QuestionResponseDto updatePartial(Long id, UpdateQuestionDto updateQuestionDto);
    void delete(Long id);
    List<Question> findRandomQuestionsByCategories(int numQuestions, Set<Long> categories);
    Question findQuestionEntityById(Long id);
}
