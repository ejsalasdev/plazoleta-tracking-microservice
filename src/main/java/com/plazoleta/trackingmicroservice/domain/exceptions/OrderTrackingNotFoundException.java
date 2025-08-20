package com.plazoleta.trackingmicroservice.domain.exceptions;

public class OrderTrackingNotFoundException extends RuntimeException {
    public OrderTrackingNotFoundException(String message) {
        super(message);
    }
}
