package com.learning.quiz_api.impl;

import com.learning.quiz_api.DTOs.requests.CreateQuizDto;
import com.learning.quiz_api.DTOs.requests.SubmitQuizDto;
import com.learning.quiz_api.DTOs.responses.QuestionResponseDto;
import com.learning.quiz_api.DTOs.responses.QuizResponseDto;
import com.learning.quiz_api.DTOs.responses.QuizResultDto;
import com.learning.quiz_api.*;
import com.learning.quiz_api.entities.AppUser;
import com.learning.quiz_api.entities.Question;
import com.learning.quiz_api.entities.Quiz;
import com.learning.quiz_api.repositories.QuizRepository;
import com.learning.quiz_api.services.QuestionService;
import com.learning.quiz_api.services.QuizService;
import com.learning.quiz_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuestionService questionService;
    private final UserService userService;

    @Override
    @Transactional
    public QuizResponseDto createQuiz(CreateQuizDto createQuizDto, Long userId) {

        AppUser user = userService.findById(userId);

        List<Question> questions = questionService.findRandomQuestionsByCategories(
                createQuizDto.getNumQuestions(),
                createQuizDto.getCategories()
        );

        if (questions.size() < createQuizDto.getNumQuestions()) {
            throw new RuntimeException("Not enough questions available for the selected categories");
        }

        HashSet<Question> quzQuestions = new HashSet<>();

        // TODO: Anton - create function to select random questions

        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setQuestions(quzQuestions);
        quiz.setFinished(false);
        quiz.setCorrectAnswers(0);
        quiz.setMarkedForDeletion(false);
        Quiz savedQuiz = quizRepository.save(quiz);

        return mapQuizToResponseDto(savedQuiz);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizResponseDto findById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .filter(q -> !q.isMarkedForDeletion())
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        return mapQuizToResponseDto(quiz);
    }

    @Override
    public QuizResponseDto findByIdForUser(Long id, Long userId) {
        Optional<Quiz> quiz = quizRepository.findByIdAndUserId(id, userId);

        if(quiz.isEmpty())
            throw new RuntimeException("Quiz not found with id: " + id);

        return mapQuizToResponseDto(quiz.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDto> findAll() {
        return quizRepository.findAllNotMarkedForDeletion().stream()
                .map(this::mapQuizToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .filter(q -> !q.isMarkedForDeletion())
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        quiz.setMarkedForDeletion(true);
        quizRepository.save(quiz);
    }


    @Override
    @Transactional
    public QuizResultDto submitQuiz(Long quizId, SubmitQuizDto submitQuizDto, Long userId) {

        Optional<Quiz> oQuiz = quizRepository.findByIdAndUserId(quizId, userId);

        if(oQuiz.isEmpty())
            throw new RuntimeException("Quiz not found with id: " + quizId);

        Quiz quiz = oQuiz.get();

        if (quiz.isFinished()) {
            throw new IllegalStateException("Quiz is already finished");
        }

        int correctAnswers = 0;
        Map<Long, Set<Integer>> correctAnswersMap = new HashMap<>();

        for (Question question : quiz.getQuestions()) {
            Set<Integer> correctOptions = question.getCorrectOptions();
            correctAnswersMap.put(question.getId(), correctOptions);

            Set<Integer> userAnswers = submitQuizDto.getQuestionAnswers().get(question.getId());
            if (userAnswers != null && userAnswers.equals(correctOptions)) {
                correctAnswers++;
            }
        }
        quiz.setFinished(true);
        quiz.setCorrectAnswers(correctAnswers);
        quizRepository.save(quiz);


        return mapQuizToResultDto(quizId, quiz.getQuestions().size(), correctAnswers,
                submitQuizDto.getQuestionAnswers(), correctAnswersMap);
    }


    private QuizResponseDto mapQuizToResponseDto(Quiz quiz) {
        QuizResponseDto dto = new QuizResponseDto();
        dto.setId(quiz.getId());
        dto.setUserId(quiz.getUser().getId());
        dto.setFinished(quiz.isFinished());
        dto.setCorrectAnswers(quiz.getCorrectAnswers());


        Set<QuestionResponseDto> questionDtos = quiz.getQuestions().stream()
                .map(this::mapQuestionToResponseDto)
                .collect(Collectors.toSet());
        dto.setQuestions(questionDtos);

        return dto;
    }

    private QuestionResponseDto mapQuestionToResponseDto(Question question) {
        QuestionResponseDto dto = new QuestionResponseDto();
        dto.setId(question.getId());
        dto.setCategory(question.getCategory().getName());
        dto.setDescription(question.getDescription());
        dto.setQuestionType(question.getQuestionType());
        dto.setOptions(new HashSet<>(question.getOptions()));
        dto.setExplanation(question.getExplanation());
        return dto;
    }

    private QuizResultDto mapQuizToResultDto(Long quizId, int totalQuestions, int correctAnswers,
                                             Map<Long, Set<Integer>> userAnswers,
                                             Map<Long, Set<Integer>> correctAnswersMap) {
        QuizResultDto result = new QuizResultDto();
        result.setQuizId(quizId);
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(correctAnswers);
        result.setUserAnswers(new HashMap<>(userAnswers));
        result.setCorrectAnswersMap(new HashMap<>(correctAnswersMap));
        return result;
    }

}
