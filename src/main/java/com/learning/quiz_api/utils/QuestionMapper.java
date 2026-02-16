package com.learning.quiz_api.utils;

import com.learning.quiz_api.DTOs.requests.CreateQuestionDto;
import com.learning.quiz_api.DTOs.requests.UpdateQuestionDto;
import com.learning.quiz_api.DTOs.responses.QuestionResponseDto;
import com.learning.quiz_api.entities.Question;
import com.learning.quiz_api.entities.Category;
import com.learning.quiz_api.services.CategoryService;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class QuestionMapper {

    private final CategoryService categoryService;

    public QuestionMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Question createQuestionDtoToQuestion(CreateQuestionDto createQuestionDto) {
        if (createQuestionDto == null) {
            return null;
        }

        Question question = new Question();
        Category category = categoryService.getCategoryById(createQuestionDto.getCategoryId());
        question.setCategory(category);
        question.setDescription(createQuestionDto.getDescription());
        question.setQuestionType(createQuestionDto.getQuestionType());

        question.setOptions(new HashSet<>(createQuestionDto.getOptions()));
        question.setCorrectOptions(new HashSet<>(createQuestionDto.getCorrectOptions()));

        question.setExplanation(createQuestionDto.getExplanation());
        question.setMarkedForDeletion(false);

        return question;
    }

    public QuestionResponseDto questionToQuestionResponseDto(Question question) {
        if (question == null) {
            return null;
        }

        QuestionResponseDto responseDto = new QuestionResponseDto();
        responseDto.setId(question.getId());

        responseDto.setCategory(question.getCategory().getName());
        responseDto.setDescription(question.getDescription());
        responseDto.setQuestionType(question.getQuestionType());


        responseDto.setOptions(new HashSet<>(question.getOptions()));

        responseDto.setExplanation(question.getExplanation());

        return responseDto;
    }

    public void updateQuestionFromDto(UpdateQuestionDto dto, Question question) {
        if (dto == null || question == null) {
            return;
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(dto.getCategoryId());
            question.setCategory(category);
        }
        if (dto.getDescription() != null) {
            question.setDescription(dto.getDescription());
        }
        if (dto.getQuestionType() != null) {
            question.setQuestionType(dto.getQuestionType());
        }
        if (dto.getOptions() != null) {
            question.setOptions(new HashSet<>(dto.getOptions()));
        }
        if (dto.getCorrectOptions() != null) {
            question.setCorrectOptions(new HashSet<>(dto.getCorrectOptions()));
        }
        if (dto.getExplanation() != null) {
            question.setExplanation(dto.getExplanation());
        }
    }
}