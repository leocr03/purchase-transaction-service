package com.purchasetransaction.exception;

import com.purchasetransaction.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.List;

import static com.purchasetransaction.constant.MessageConstants.INTERNAL_ERROR_MESSAGE;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        final String finalErrorMessage = String.join(", ", errors);
        return getApiError(HttpStatus.BAD_REQUEST, finalErrorMessage);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            EmptyResultDataAccessException ex) {
        return getApiError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleValidationErrors(Exception ex) {
        log.error("Some internal error occurred. ", ex);
        return getApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                INTERNAL_ERROR_MESSAGE);
    }

    private static ResponseEntity<ApiError> getApiError(HttpStatus httpStatus,
                                                        String ex) {
        return new ResponseEntity<>(new ApiError(httpStatus, ZonedDateTime.now(),
                ex), httpStatus);
    }
}
