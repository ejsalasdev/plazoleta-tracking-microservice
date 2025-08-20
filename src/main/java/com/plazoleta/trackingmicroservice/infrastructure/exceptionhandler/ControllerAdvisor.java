package com.plazoleta.trackingmicroservice.infrastructure.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.plazoleta.trackingmicroservice.domain.exceptions.InvalidOrderIdException;
import com.plazoleta.trackingmicroservice.domain.exceptions.InvalidOrderTrackingRequestException;
import com.plazoleta.trackingmicroservice.domain.exceptions.OrderTrackingNotFoundException;
import com.plazoleta.trackingmicroservice.domain.exceptions.UnauthorizedOrderAccessException;
import com.plazoleta.trackingmicroservice.domain.utils.constants.DomainMessagesConstants;

@ControllerAdvice
public class ControllerAdvisor {

        @ExceptionHandler(OrderTrackingNotFoundException.class)
        public ResponseEntity<ExceptionResponse> handleOrderTrackingNotFoundException(OrderTrackingNotFoundException exception) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(UnauthorizedOrderAccessException.class)
        public ResponseEntity<ExceptionResponse> handleUnauthorizedOrderAccessException(UnauthorizedOrderAccessException exception) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler({InvalidOrderIdException.class, InvalidOrderTrackingRequestException.class})
        public ResponseEntity<ExceptionResponse> handleInvalidRequestExceptions(RuntimeException exception) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ExceptionResponse> handleGenericException(Exception exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ExceptionResponse(DomainMessagesConstants.INTERNAL_ERROR, LocalDateTime.now()));
        }
}
