package com.plazoleta.trackingmicroservice.domain.ports.out.external;

import java.util.Optional;

public interface RestaurantServicePort {
    
    Optional<Long> getRestaurantIdByOwnerId(Long ownerId);
}
