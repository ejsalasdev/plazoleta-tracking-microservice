package com.plazoleta.trackingmicroservice.domain.ports.in;

import java.util.List;

import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;

/**
 * Input port for order tracking operations
 * Defines the operations that can be performed on order tracking records
 */
public interface OrderTrackingServicePort {

    /**
     * Creates a new order tracking record
     * Used by internal endpoint from foodcourt-microservice
     */
    OrderTrackingModel createOrderTracking(OrderTrackingModel orderTracking);

    /**
     * Gets the tracking history for a specific order
     * Validates that the requesting customer owns the order
     */
    List<OrderTrackingModel> getOrderTrackingHistory(Long orderId);
}
