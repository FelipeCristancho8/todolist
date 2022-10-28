package com.felipe.todolist.infraestructure.exception;

import com.felipe.todolist.infraestructure.model.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "com.felipe.todolist.infraestructure.controllers")
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> illegalArgumentExceptionHandler(IllegalArgumentException e){
        return new ResponseEntity<>(new ApiError("Solicitud errada", e.getMessage().split(System.lineSeparator())),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> noSuchElementExceptionHandler(NoSuchElementException e){
        return new ResponseEntity<>(new ApiError("Solicitud errada", e.getMessage().split(System.lineSeparator())),
                HttpStatus.NOT_FOUND);
    }
}
