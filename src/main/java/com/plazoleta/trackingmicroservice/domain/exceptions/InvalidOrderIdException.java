package com.plazoleta.trackingmicroservice.domain.exceptions;

public class InvalidOrderIdException extends RuntimeException {
    public InvalidOrderIdException(String message) {
        super(message);
    }
}
