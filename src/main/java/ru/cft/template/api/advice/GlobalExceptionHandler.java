package ru.cft.template.api.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.cft.template.api.model.error.ErrorResponse;
import ru.cft.template.api.model.error.factory.ErrorResponseFactory;
import ru.cft.template.core.exception.CredentialAlreadyUsedException;
import ru.cft.template.core.exception.DoesntHaveRightsException;
import ru.cft.template.core.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponseFactory.createValidationErrorResponse(ex, request);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponseFactory.createConstraintViolationErrorResponse(ex.getConstraintViolations(), request);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DoesntHaveRightsException.class)
    public ResponseEntity<ErrorResponse> handleDoesntHaveRightsException(DoesntHaveRightsException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponseFactory.createSimpleErrorResponse(HttpStatus.FORBIDDEN, "Doesn't have rights", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(CredentialAlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleCredentialAlreadyUsedException(CredentialAlreadyUsedException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponseFactory.createSimpleErrorResponse(HttpStatus.BAD_REQUEST, "Credentials error", ex.getMessage(), request);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponseFactory.createSimpleErrorResponse(HttpStatus.NOT_FOUND, "Not found", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponseFactory.createSimpleErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication error", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}

