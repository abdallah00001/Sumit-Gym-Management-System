package com.summit.gym.Sumit_Gym_Management_System.exception_handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> jakartaValidationExceptionHandler(MethodArgumentNotValidException e) {
        StringBuilder errorMsgBuilder = new StringBuilder();

        e.getBindingResult().getAllErrors()
                .forEach(objectError -> {
                    errorMsgBuilder.append(objectError.getDefaultMessage());
                    errorMsgBuilder.append(System.lineSeparator());
                });

        return ResponseEntity
                .badRequest()
                .body(errorMsgBuilder.toString());
    }


}
