package com.learning.quiz_api.exceptions;

public class QuestionDeletionException extends RuntimeException {
    public QuestionDeletionException(Long id) {
        super("Question with id " + id + " is already marked for deletion");
    }
}
