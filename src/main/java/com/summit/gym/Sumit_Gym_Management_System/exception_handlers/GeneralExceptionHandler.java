package com.summit.gym.Sumit_Gym_Management_System.exception_handlers;

import com.summit.gym.Sumit_Gym_Management_System.exceptions.ActiveShiftReassignedException;
import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler {

    //Exception is not referring to a faulty state
    //Just to notify the user that the shift is not new, and he was reassigned old active shift
    @ExceptionHandler(ActiveShiftReassignedException.class)
    public ResponseEntity<String> reassignedShiftHandler(ActiveShiftReassignedException e) {
        return ResponseEntity.ok(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String message = "Invalid data, please check your input";
        String errorMsg = e.getMessage();

        for (String uniqueFieldName : ValidationUtil.UNIQUE_FIELD_NAMES) {
            if (errorMsg.contains("unique_" + uniqueFieldName)) {
                message = uniqueFieldName + " already exists and must be unique";
            }
        }
        return ResponseEntity
                .badRequest()
                .body(message);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundExceptions(EntityNotFoundException e) {
//        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<String> handleIllegalArgException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    //    Validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> jakartaValidationExceptionHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();
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

    private String extractMsgFromConstraintViolationException(ConstraintViolationException e) {
        StringBuilder builder = new StringBuilder();
        e.getConstraintViolations()
                .forEach(constraintViolation ->
                {
                    builder.append(constraintViolation.getMessage());
                    builder.append(System.lineSeparator());
                });
        return builder.toString();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        String msg = extractMsgFromConstraintViolationException(e);
        return ResponseEntity
                .badRequest()
                .body(msg);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<String> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        e.printStackTrace();
        StringBuilder builder = new StringBuilder();
        e.getAllValidationResults()
                .forEach(parameterValidationResult -> parameterValidationResult
                        .getResolvableErrors().forEach(messageSourceResolvable -> {
                            builder.append(messageSourceResolvable.getDefaultMessage());
                        }));
        return ResponseEntity
                .badRequest()
                .body(builder.toString());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> handleTransactionSystemException(TransactionSystemException e) {
        e.printStackTrace();
        //Default msg
        String msg = e.getMessage();

        Throwable cause = e.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            msg = extractMsgFromConstraintViolationException((ConstraintViolationException) cause);
        }

        return ResponseEntity
                .badRequest()
                .body(msg);

    }

    //Safety net
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerError(Exception e) {
        e.printStackTrace();
        final String errorMsg = "Something went wrong please contact the support";
        return ResponseEntity
                .internalServerError()
                .body(errorMsg + "\n" +
                        e.getMessage());
    }

}
