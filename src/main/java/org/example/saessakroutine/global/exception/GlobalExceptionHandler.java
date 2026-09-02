package org.example.saessakroutine.global.exception;

import org.example.saessakroutine.user.exception.PasswordMismatchException;
import org.example.saessakroutine.user.exception.UserAlreadyExistsException;
import org.example.saessakroutine.user.exception.UserNotFoundException;
import org.example.saessakroutine.user.exception.password.VerificationCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExists(
            UserAlreadyExistsException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "type", "userAlreadyExists",
                        "statusCode", 409
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "type", "validationError",
                        "statusCode", 400
                ));
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Map<String, Object>> handlePasswordMismatch(
            PasswordMismatchException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "type", "passwordMismatch",
                        "message", exception.getMessage(),
                        "statusCode", 401
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(
            UserNotFoundException exception
    ) {
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "type", "userNotFound",
                        "statusCode", 404
                ));
    }

    @ExceptionHandler(VerificationCodeException.class)
    public ResponseEntity<Map<String, Object>> handleVerificationCodeException(
            VerificationCodeException exception
    ) {
        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "type", "verificationCodeError",
                        "message", exception.getMessage(),
                        "statusCode", 400
                ));
    }
}
