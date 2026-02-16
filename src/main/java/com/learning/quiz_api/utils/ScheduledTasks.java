package com.learning.quiz_api.utils;

import com.learning.quiz_api.services.QuestionService;
import com.learning.quiz_api.services.QuizService;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final QuestionService questionService;
    private final QuizService quizService;

    public ScheduledTasks(QuestionService questionService, QuizService quizService) {
        this.questionService = questionService;
        this.quizService = quizService;
    }
}
