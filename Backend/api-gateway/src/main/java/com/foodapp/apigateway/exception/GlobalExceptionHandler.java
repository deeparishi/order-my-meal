package com.foodapp.apigateway.exception;

import com.foodapp.common.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnAuthorizedException.class)
    public Mono<ResponseEntity<GenericResponse<String>>> handleTokenExpired(UnAuthorizedException ex) {
        GenericResponse<String> errorResponse = GenericResponse.error(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.name(),
                ex.getMessage()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse));
    }


}
