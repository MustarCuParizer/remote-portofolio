package com.learning.quiz_api.controller;

import com.learning.quiz_api.DTOs.requests.CreateQuestionDto;
import com.learning.quiz_api.DTOs.requests.UpdateQuestionDto;
import com.learning.quiz_api.DTOs.responses.QuestionResponseDto;
import com.learning.quiz_api.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/v1/question")
@RequiredArgsConstructor
public class AdminQuestionController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions() {
        return ResponseEntity.ok(questionService.findAll());
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(
            @Valid @RequestBody CreateQuestionDto createQuestionDto
    ) {
        QuestionResponseDto response = questionService.save(createQuestionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.delete(id); // This will mark the question for deletion
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuestionDto updateQuestionDto
    ) {
        QuestionResponseDto response = questionService.update(id, updateQuestionDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> updateQuestionPartial(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuestionDto updateQuestionDto
    ) {
        QuestionResponseDto response = questionService.updatePartial(id, updateQuestionDto);
        return ResponseEntity.ok(response);
    }
}
