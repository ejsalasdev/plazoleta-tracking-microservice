package com.plazoleta.trackingmicroservice.application.utils.constants;

public final class ApplicationMessagesConstants {

    private ApplicationMessagesConstants() {
        throw new IllegalStateException("Utility class");
    }

    // Success messages
    public static final String ORDER_TRACKING_RETRIEVED_SUCCESSFULLY = "Order tracking history retrieved successfully.";
    public static final String ORDER_STATUS_CHANGE_RECORDED_SUCCESSFULLY = "Order status change recorded successfully.";
    
    // Error messages
    public static final String ORDER_TRACKING_NOT_FOUND = "No tracking information found for this order.";
    public static final String UNAUTHORIZED_ACCESS = "You are not authorized to access this resource.";
    public static final String INVALID_ORDER_ID = "The provided order ID is invalid.";
    public static final String INVALID_REQUEST = "The request contains invalid data.";
    
    // Validation messages
    public static final String ORDER_ID_REQUIRED = "Order ID is required.";
    public static final String CUSTOMER_ID_REQUIRED = "Customer ID is required.";
    public static final String STATUS_REQUIRED = "Order status is required.";
    public static final String TIMESTAMP_REQUIRED = "Timestamp is required.";
}
