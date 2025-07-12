package com.plazoleta.trackingmicroservice.domain.exceptions;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
