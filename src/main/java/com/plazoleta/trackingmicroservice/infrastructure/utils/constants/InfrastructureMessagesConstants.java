package com.plazoleta.trackingmicroservice.infrastructure.utils.constants;

public final class InfrastructureMessagesConstants {

    public static final String ORDER_TRACKING_COLLECTION = "order_tracking";

    public static final String JWT_TOKEN_EXPIRED = "JWT token has expired.";
    public static final String JWT_TOKEN_INVALID = "JWT token is invalid.";
    public static final String JWT_TOKEN_MISSING = "JWT token is missing.";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access.";
    public static final String FORBIDDEN_ACCESS = "Access forbidden.";

    public static final String FOODCOURT_SERVICE_UNAVAILABLE = "Foodcourt service is currently unavailable.";
    public static final String USER_SERVICE_UNAVAILABLE = "User service is currently unavailable.";

    public static final String DATABASE_CONNECTION_ERROR = "Database connection error.";
    public static final String DOCUMENT_NOT_FOUND = "Document not found in database.";
    public static final String DATABASE_SAVE_ERROR = "Error saving document to database.";

    public static final String VALIDATION_ERROR = "Validation error occurred.";
    public static final String INVALID_DOCUMENT_FORMAT = "Invalid document format.";

    private InfrastructureMessagesConstants() {
        throw new IllegalStateException("Utility class");
    }
}
