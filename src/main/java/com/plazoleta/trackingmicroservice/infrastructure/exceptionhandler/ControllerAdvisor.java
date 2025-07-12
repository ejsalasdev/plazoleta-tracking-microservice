package com.plazoleta.trackingmicroservice.infrastructure.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ExceptionResponse> handleGenericException(Exception exception) {
                String message = "An unexpected error occurred. Please contact support if the problem persists.";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ExceptionResponse(message, LocalDateTime.now()));
        }
}
