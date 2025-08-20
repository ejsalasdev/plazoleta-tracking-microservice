package com.plazoleta.trackingmicroservice.domain.utils.constants;

public final class DomainMessagesConstants {

    public static final String CLIENT_ROLE = "CLIENT";
    public static final String EMPLOYEE_ROLE = "EMPLOYEE";
    public static final String OWNER_ROLE = "OWNER";
    public static final String ADMIN_ROLE = "ADMIN";

    public static final String ORDER_TRACKING_NOT_FOUND = "Order tracking history not found.";
    public static final String ORDER_TRACKING_SAVED_SUCCESSFULLY = "Order tracking record saved successfully.";
    public static final String ORDER_TRACKING_UNAUTHORIZED_ACCESS = "You are not authorized to view this order tracking information.";
    public static final String ORDER_TRACKING_INVALID_ORDER_ID = "Invalid order ID provided.";
    public static final String ORDER_TRACKING_INVALID_STATUS = "Invalid order status provided.";
    public static final String ORDER_TRACKING_INVALID_REQUEST = "Invalid order tracking request provided.";

    public static final String CLIENT_NOT_AUTHORIZED = "Only clients can view order tracking information.";
    public static final String ORDER_NOT_BELONGS_TO_CLIENT = "This order does not belong to the authenticated client.";

    public static final String STATUS_CHANGE_RECORDED = "Order status change recorded successfully.";
    public static final String INVALID_STATUS_TRANSITION = "Invalid status transition.";

    public static final String INTERNAL_ERROR = "An internal error occurred while processing the request.";
    public static final String INVALID_REQUEST_DATA = "Invalid request data provided.";
    public static final String ORDER_TRACKING_INVALID_RESTAURANT_ID = "Restaurant ID is required.";
    public static final String ORDER_NOT_BELONGS_TO_RESTAURANT = "Order does not belong to the specified restaurant.";
    public static final String RESTAURANT_NOT_BELONGS_TO_OWNER = "Restaurant does not belong to the current owner.";

    private DomainMessagesConstants() {
        throw new IllegalStateException("Utility class");
    }
}
