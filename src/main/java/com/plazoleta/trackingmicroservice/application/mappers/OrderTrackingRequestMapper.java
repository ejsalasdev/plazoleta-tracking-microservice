package com.plazoleta.trackingmicroservice.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.plazoleta.trackingmicroservice.application.dto.request.OrderTrackingRequest;
import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderTrackingRequestMapper {

    OrderTrackingModel requestToModel(OrderTrackingRequest request);

}
