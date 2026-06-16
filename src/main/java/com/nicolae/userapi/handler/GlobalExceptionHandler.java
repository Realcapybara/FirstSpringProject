package com.nicolae.userapi.handler;

import com.nicolae.userapi.exception.UserAlreadyExistsException;
import com.nicolae.userapi.exception.UserNotFoundException;
import com.nicolae.userapi.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        /* takes the custom message: User not found: DoesNotExist */
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND) /*404 Not Found*/
                .body(errorResponse); /* "message": "User not found: DoesNotExist"*/
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT) /*404 Not Found*/
                .body(errorResponse);
    }
}