package com.learning.quiz_api.controller;

import com.learning.quiz_api.DTOs.requests.CreateQuizDto;
import com.learning.quiz_api.DTOs.requests.SubmitQuizDto;
import com.learning.quiz_api.DTOs.responses.QuizResponseDto;
import com.learning.quiz_api.DTOs.responses.QuizResultDto;
import com.learning.quiz_api.services.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/v1/quiz")
@RequiredArgsConstructor
public class PublicQuizController {
    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<QuizResponseDto> createQuiz(@Valid @RequestBody CreateQuizDto createQuizDto, @AuthenticationPrincipal Long userId) {
        QuizResponseDto response = quizService.createQuiz(createQuizDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> getQuiz(@PathVariable Long quizId, @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(quizService.findByIdForUser(quizId, userId));
    }

    @PostMapping("/{quizId}/finish")
    public ResponseEntity<QuizResultDto> finishQuiz(
            @PathVariable Long quizId,
            @Valid @RequestBody SubmitQuizDto submitQuizDto,
            @AuthenticationPrincipal Long userId
    ) {
        return ResponseEntity.ok(quizService.submitQuiz(quizId, submitQuizDto, userId));
    }

}

