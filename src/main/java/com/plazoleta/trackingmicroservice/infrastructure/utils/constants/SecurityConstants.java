package com.plazoleta.trackingmicroservice.infrastructure.utils.constants;

public final class SecurityConstants {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    public static final String ROLE_OWNER = "OWNER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }
}
