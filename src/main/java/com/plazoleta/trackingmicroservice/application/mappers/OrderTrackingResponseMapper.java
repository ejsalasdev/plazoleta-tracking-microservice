package com.plazoleta.trackingmicroservice.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.plazoleta.trackingmicroservice.application.dto.response.OrderTrackingResponse;
import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderTrackingResponseMapper {

    OrderTrackingResponse modelToResponse(OrderTrackingModel orderTrackingModel);

    List<OrderTrackingResponse> toResponseList(List<OrderTrackingModel> orderTrackings);
}
