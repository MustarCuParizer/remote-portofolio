package com.learning.quiz_api.impl;

import com.learning.quiz_api.DTOs.requests.CreateQuestionDto;
import com.learning.quiz_api.DTOs.requests.UpdateQuestionDto;
import com.learning.quiz_api.DTOs.responses.QuestionResponseDto;
import com.learning.quiz_api.entities.Category;
import com.learning.quiz_api.entities.Question;
import com.learning.quiz_api.repositories.CategoryRepository;
import com.learning.quiz_api.repositories.QuestionRepository;
import com.learning.quiz_api.services.QuestionService;
import com.learning.quiz_api.utils.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public QuestionResponseDto save(CreateQuestionDto createQuestionDto) {
        Optional<Category> category = categoryRepository.findById(createQuestionDto.getCategoryId());
        if (category.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        Question question = questionMapper.createQuestionDtoToQuestion(createQuestionDto);
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.questionToQuestionResponseDto(savedQuestion);
    }

    @Override
    public List<QuestionResponseDto> findAll() {
        var dbQuestions = questionRepository.findAll();

        List<QuestionResponseDto> response = new ArrayList<>();

        for (var question: dbQuestions) {
            response.add(questionMapper.questionToQuestionResponseDto(question));
        }

        return response;
    }

    @Override
    public QuestionResponseDto findById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public QuestionResponseDto update(Long id, UpdateQuestionDto updateQuestionDto) {
        Question question = questionRepository.findById(id)
                .filter(q -> !q.isMarkedForDeletion())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        questionMapper.updateQuestionFromDto(updateQuestionDto, question);
        Question updatedQuestion = questionRepository.save(question);
        return questionMapper.questionToQuestionResponseDto(updatedQuestion);
    }

    @Override
    @Transactional
    public QuestionResponseDto updatePartial(Long id, UpdateQuestionDto updateQuestionDto) {
        Question question = questionRepository.findById(id)
                .filter(q -> !q.isMarkedForDeletion())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        questionMapper.updateQuestionFromDto(updateQuestionDto, question);
        Question updatedQuestion = questionRepository.save(question);
        return questionMapper.questionToQuestionResponseDto(updatedQuestion);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        Question question = questionRepository.findById(id)
                .filter(q -> !q.isMarkedForDeletion())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
        question.setMarkedForDeletion(true);
        questionRepository.save(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findRandomQuestionsByCategories(int numQuestions, Set<Long> categories) {
        List<Question> questions;
        if (categories == null || categories.isEmpty()) {
            questions = questionRepository.findAllActive();
            System.out.println("####1");
        } else {
            questions = questionRepository.findByCategoryIdIn(categories);
            System.out.println("###2");
        }


        return questions;
    }

    @Override
    @Transactional(readOnly = true)
    public Question findQuestionEntityById(Long id) {
        return questionRepository.findById(id)
                .filter(q -> !q.isMarkedForDeletion())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }
}

