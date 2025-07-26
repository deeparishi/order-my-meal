package com.foodapp.paymentservice.exception;

import com.foodapp.common.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFound.class)
    public ResponseEntity<GenericResponse<Object>> handleNotFoundException(NotFound ex) {
        return new ResponseEntity<>(
                GenericResponse.error(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        "NOT FOUND"),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(KafkaException.class)
    public ResponseEntity<GenericResponse<Object>> handleKafkaException(NotFound ex) {
        return new ResponseEntity<>(
                GenericResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage(),
                        "Error occurred on kafka"),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<GenericResponse<Object>> handleFeignClientException(NotFound ex) {
        return new ResponseEntity<>(
                GenericResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage(),
                        "Error occurred on feign call"),
                HttpStatus.NOT_FOUND
        );
    }
}
