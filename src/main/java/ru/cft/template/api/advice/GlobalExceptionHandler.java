package ru.cft.template.api.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.cft.template.api.error.utility.ErrorResponseUtility;
import ru.cft.template.api.model.error.ErrorResponse;
import ru.cft.template.core.exception.*;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createValidationErrorResponse(ex, request);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createConstraintViolationErrorResponse(
                ex.getConstraintViolations(), request
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DoesntHaveRightsException.class)
    public ResponseEntity<ErrorResponse> handleDoesntHaveRightsException(
            DoesntHaveRightsException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createSimpleErrorResponse(
                HttpStatus.FORBIDDEN,
                ErrorName.DOEST_HAVE_RIGHTS,
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(CredentialAlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleCredentialAlreadyUsedException(
            CredentialAlreadyUsedException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createSimpleErrorResponse(
                HttpStatus.BAD_REQUEST,
                ErrorName.CREDENTIALS_ERROR,
                ex.getMessage(),
                request
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createSimpleErrorResponse(
                HttpStatus.NOT_FOUND,
                ErrorName.NOT_FOUND,
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createSimpleErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ErrorName.AUTHENTICATION_ERROR,
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWalletException(
            WalletNotFoundException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createSimpleErrorResponse(
                HttpStatus.NOT_FOUND,
                ErrorName.NOT_FOUND,
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BalanceLessThanTransferException.class)
    public ResponseEntity<ErrorResponse> handleBalanceException(
            TransferNotFoundException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createSimpleErrorResponse(
                HttpStatus.NOT_FOUND,
                ErrorName.NOT_FOUND,
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(TransferNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransferException(
            TransferNotFoundException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createSimpleErrorResponse(
                HttpStatus.NOT_FOUND,
                ErrorName.NOT_FOUND,
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SelfTransferException.class)
    public ResponseEntity<ErrorResponse> handleSelfTransactionException(
            SelfTransferException ex,
            WebRequest request
    ) {

        ErrorResponse errorResponse = ErrorResponseUtility.createSimpleErrorResponse(
                HttpStatus.BAD_REQUEST,
                ErrorName.SELF_TRANSACTION,
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}

