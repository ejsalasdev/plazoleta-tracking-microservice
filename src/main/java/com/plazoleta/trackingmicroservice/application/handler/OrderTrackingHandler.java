package com.plazoleta.trackingmicroservice.application.handler;

import java.util.List;

import com.plazoleta.trackingmicroservice.application.dto.request.OrderTrackingRequest;
import com.plazoleta.trackingmicroservice.application.dto.response.OrderTrackingResponse;

public interface OrderTrackingHandler {

    // Para endpoint interno (foodcourt-microservice)
    OrderTrackingResponse createOrderTracking(OrderTrackingRequest request);

    // Para endpoint de consulta (cliente)
    List<OrderTrackingResponse> getOrderTrackingHistory(Long orderId);

}
