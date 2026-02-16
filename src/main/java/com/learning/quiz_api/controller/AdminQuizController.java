package com.learning.quiz_api.controller;

import com.learning.quiz_api.DTOs.responses.QuizResponseDto;
import com.learning.quiz_api.services.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/v1/quiz")
@RequiredArgsConstructor
public class AdminQuizController {
    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizResponseDto>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.delete(id); // This will mark the quiz for deletion
        return ResponseEntity.noContent().build();
    }
}
