package com.plazoleta.trackingmicroservice.domain.ports.out;

import java.util.List;

/**
 * Port for getting authenticated user information
 * Provides access to current user context from security layer
 */
public interface AuthenticatedUserPort {
    
    /**
     * Gets the current authenticated user ID
     * @return the user ID
     */
    Long getCurrentUserId();
    
    /**
     * Gets the current authenticated user ID (alias for getCurrentUserId)
     * @return the user ID
     */
    default Long getAuthenticatedUserId() {
        return getCurrentUserId();
    }
    
    /**
     * Gets the current authenticated user roles
     * @return list of user roles
     */
    List<String> getCurrentUserRoles();
}
