package ru.cft.template.api.model.error.factory;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import ru.cft.template.api.model.error.ErrorResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ErrorResponseFactory {

    private static final String VALIDATION_ERROR_NAME = "Validation error";
    private static final String VALIDATION_ERROR_MESSAGE = "There were validation errors in the request";

    public static ErrorResponse createValidationErrorResponse(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(field, errorMessage);
        });

        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                VALIDATION_ERROR_NAME,
                VALIDATION_ERROR_MESSAGE,
                errors,
                request
        );
    }

    public static ErrorResponse createConstraintViolationErrorResponse(
            Set<ConstraintViolation<?>> violations, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        violations.forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                VALIDATION_ERROR_NAME,
                VALIDATION_ERROR_MESSAGE,
                errors,
                request
        );
    }

    public static ErrorResponse createSimpleErrorResponse(
            HttpStatus status, String error, String message, WebRequest request) {

        return createErrorResponse(status, error, message, Map.of(), request);
    }

    private static ErrorResponse createErrorResponse(
            HttpStatus status, String error, String message,
            Map<String, String> details, WebRequest request) {

        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                details,
                request.getDescription(false)
        );
    }
}
