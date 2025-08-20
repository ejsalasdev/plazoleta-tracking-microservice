package com.plazoleta.trackingmicroservice.infrastructure.exceptions;

public class ErrorDecodeAuthoritiesException extends IllegalArgumentException {

    public ErrorDecodeAuthoritiesException(String message) {
        super(message);
    }

}
