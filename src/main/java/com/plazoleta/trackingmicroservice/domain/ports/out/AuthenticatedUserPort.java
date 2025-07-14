package com.plazoleta.trackingmicroservice.domain.ports.out;

import java.util.List;

public interface AuthenticatedUserPort {

    Long getCurrentUserId();

    default Long getAuthenticatedUserId() {
        return getCurrentUserId();
    }

    List<String> getCurrentUserRoles();
}
