package com.felipe.todolist.infraestructure.exception;

import com.felipe.todolist.infraestructure.model.ToDoListError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "com.felipe.todolist.infraestructure.controllers")
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ToDoListError> illegalArgumentExceptionHandler(IllegalArgumentException e){
        return new ResponseEntity(new ToDoListError("Solicitud errada", e.getMessage().split(System.lineSeparator())),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity noSuchElementExceptionHandler(NoSuchElementException e){
        return new ResponseEntity(new ToDoListError("Solicitud errada", e.getMessage().split(System.lineSeparator())),
                HttpStatus.NOT_FOUND);
    }


    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<ToDoListError> exceptionHandler(Exception e){
        return new ResponseEntity<>(new ToDoListError("Error inesperado", new String[]{e.getMessage()}), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
}
