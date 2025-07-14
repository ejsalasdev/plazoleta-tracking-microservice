package com.plazoleta.trackingmicroservice.domain.exceptions;

public class InvalidOrderTrackingRequestException extends RuntimeException {
    public InvalidOrderTrackingRequestException(String message) {
        super(message);
    }
}
