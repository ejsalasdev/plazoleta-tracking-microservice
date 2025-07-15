package com.plazoleta.trackingmicroservice.application.handler.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plazoleta.trackingmicroservice.application.dto.request.OrderTrackingRequest;
import com.plazoleta.trackingmicroservice.application.dto.response.OrderEfficiencyResponse;
import com.plazoleta.trackingmicroservice.application.dto.response.OrderTrackingResponse;
import com.plazoleta.trackingmicroservice.application.handler.OrderTrackingHandler;
import com.plazoleta.trackingmicroservice.application.mappers.OrderEfficiencyResponseMapper;
import com.plazoleta.trackingmicroservice.application.mappers.OrderTrackingRequestMapper;
import com.plazoleta.trackingmicroservice.application.mappers.OrderTrackingResponseMapper;
import com.plazoleta.trackingmicroservice.domain.model.OrderEfficiencyModel;
import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;
import com.plazoleta.trackingmicroservice.domain.ports.in.OrderTrackingServicePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderTrackingHandlerImpl implements OrderTrackingHandler {

    private final OrderTrackingRequestMapper requestMapper;
    private final OrderTrackingServicePort orderTrackingServicePort;
    private final OrderTrackingResponseMapper responseMapper;
    private final OrderEfficiencyResponseMapper efficiencyResponseMapper;

    @Override
    public OrderTrackingResponse createOrderTracking(OrderTrackingRequest request) {
        OrderTrackingModel trackingModel = requestMapper.requestToModel(request);
        OrderTrackingModel savedTracking = orderTrackingServicePort.createOrderTracking(trackingModel);
        return responseMapper.modelToResponse(savedTracking);
    }

    @Override
    public List<OrderTrackingResponse> getOrderTrackingHistory(Long orderId) {
        List<OrderTrackingModel> trackingHistory = orderTrackingServicePort.getOrderTrackingHistory(orderId);
        return responseMapper.toResponseList(trackingHistory);
    }

    @Override
    public List<OrderEfficiencyResponse> getOrderEfficiencyHistory(Long restaurantId) {
        List<OrderEfficiencyModel> efficiencyModels = orderTrackingServicePort.calculateOrderEfficiency(restaurantId);
        return efficiencyResponseMapper.toEfficiencyResponseList(efficiencyModels);
    }
}
