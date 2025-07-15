package com.plazoleta.trackingmicroservice.domain.ports.out;

import java.util.List;

public interface AuthenticatedUserPort {

    Long getCurrentUserId();

    List<String> getCurrentUserRoles();
}
