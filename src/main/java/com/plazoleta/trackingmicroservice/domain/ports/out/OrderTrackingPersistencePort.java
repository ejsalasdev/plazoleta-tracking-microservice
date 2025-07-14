package com.plazoleta.trackingmicroservice.domain.ports.out;

import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;

import java.util.List;

/**
 * Output port for order tracking persistence operations
 * Defines the contract for data access operations
 */
public interface OrderTrackingPersistencePort {

    /**
     * Saves an order tracking record
     */
    OrderTrackingModel save(OrderTrackingModel orderTracking);

    /**
     * Finds all tracking records for a specific order ordered by date
     */
    List<OrderTrackingModel> findByOrderIdOrderByChangeDateAsc(Long orderId);
}
