package com.lcwd.user.service.exceptions;

import com.lcwd.user.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    In this below method  handlerResourceNotFoundException we are returning response entity
    with message and message is a class and class have three variables, so we are returning a
    message as a class object

    and in over all project if ResourceNotFoundException exception is
    generated then this method automatic get called because of @ExceptionHandler()
    */


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex) {
        String message =  ex.getMessage();
        ApiResponse response =  ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();//builder pattern is used for to build the object in single line
        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
    }
}
