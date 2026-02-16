package com.learning.quiz_api.exceptions;

public class InvalidQuestionDataException extends RuntimeException {
    public InvalidQuestionDataException(String message) {
        super(message);
    }
}