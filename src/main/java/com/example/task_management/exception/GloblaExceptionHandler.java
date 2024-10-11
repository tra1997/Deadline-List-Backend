package com.example.task_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice // "To define a common exception handling class for multiple controllers."
public class GloblaExceptionHandler {

    @ExceptionHandler(TaskAPIException.class)
    //To specify the exception handling method for a specific type of exception.
    public ResponseEntity<ErrorDetails> handleException(TaskAPIException ex, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false) //False here means that user details (session, etc.) will not be included
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
