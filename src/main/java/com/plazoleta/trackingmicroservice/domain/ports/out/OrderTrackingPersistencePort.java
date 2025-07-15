package com.plazoleta.trackingmicroservice.domain.ports.out;

import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;

import java.util.List;

public interface OrderTrackingPersistencePort {

    OrderTrackingModel save(OrderTrackingModel orderTracking);

    List<OrderTrackingModel> findByOrderIdOrderByChangeDateAsc(Long orderId);

    List<OrderTrackingModel> findByRestaurantIdOrderByChangeDateAsc(Long restaurantId);
}
