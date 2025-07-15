package com.plazoleta.trackingmicroservice.domain.ports.in;

import java.util.List;

import com.plazoleta.trackingmicroservice.domain.model.OrderEfficiencyModel;
import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;

public interface OrderTrackingServicePort {

    OrderTrackingModel createOrderTracking(OrderTrackingModel orderTracking);

    List<OrderTrackingModel> getOrderTrackingHistory(Long orderId);

    List<OrderEfficiencyModel> calculateOrderEfficiency(Long restaurantId);
}
