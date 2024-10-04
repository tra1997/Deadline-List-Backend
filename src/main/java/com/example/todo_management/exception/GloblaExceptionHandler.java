package com.example.todo_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice // để định nghĩa một lớp xử lý ngoại lệ chung cho nhiều controller
public class GloblaExceptionHandler {

    @ExceptionHandler(TodoAPIException.class)  //để chỉ định phương thức xử lý ngoại lệ cho một loại exception cụ thể.
    public ResponseEntity<ErrorDetails> handleException(TodoAPIException ex, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false) //false ở đây nghĩa là sẽ không bao gồm chi tiết người dùng (session, etc.)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
