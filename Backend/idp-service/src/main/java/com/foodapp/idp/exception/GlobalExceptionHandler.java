package com.foodapp.idp.exception;

import com.foodapp.common.utils.AppMessages;
import com.foodapp.idp.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<GenericResponse<Object>> handleTokenExpired(TokenExpiredException ex) {
        return new ResponseEntity<>(
                GenericResponse.error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), "Invalid Token"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<GenericResponse<Object>> handleNotFoundException(NotFound ex) {
        return new ResponseEntity<>(
                GenericResponse.error(HttpStatus.NOT_FOUND.value(), AppMessages.FAILED_STATUS, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GenericResponse<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(
                GenericResponse.error(HttpStatus.UNAUTHORIZED.value(), AppMessages.FAILED_STATUS, ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }
}
