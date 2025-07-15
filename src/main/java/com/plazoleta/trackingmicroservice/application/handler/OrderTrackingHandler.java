package com.plazoleta.trackingmicroservice.application.handler;

import java.util.List;

import com.plazoleta.trackingmicroservice.application.dto.request.OrderTrackingRequest;
import com.plazoleta.trackingmicroservice.application.dto.response.OrderEfficiencyResponse;
import com.plazoleta.trackingmicroservice.application.dto.response.OrderTrackingResponse;

public interface OrderTrackingHandler {

    OrderTrackingResponse createOrderTracking(OrderTrackingRequest request);

    List<OrderTrackingResponse> getOrderTrackingHistory(Long orderId);
    
    List<OrderEfficiencyResponse> getOrderEfficiencyHistory(Long restaurantId);
}
