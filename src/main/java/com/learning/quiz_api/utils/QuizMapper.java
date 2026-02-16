package com.learning.quiz_api.utils;

import com.learning.quiz_api.DTOs.responses.QuestionResponseDto;
import com.learning.quiz_api.DTOs.responses.QuizResponseDto;
import com.learning.quiz_api.entities.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuizMapper {

    private final QuestionMapper questionMapper;

    public QuizMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    public QuizResponseDto quizToQuizResponseDto(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        QuizResponseDto responseDto = new QuizResponseDto();
        responseDto.setId(quiz.getId());
        responseDto.setUserId(quiz.getUser().getId());
        responseDto.setFinished(quiz.isFinished());
        responseDto.setCorrectAnswers(quiz.getCorrectAnswers());

        Set<QuestionResponseDto> questionDtos = quiz.getQuestions().stream()
                .map(questionMapper::questionToQuestionResponseDto)
                .collect(Collectors.toSet());
        responseDto.setQuestions(questionDtos);

        return responseDto;
    }
}
