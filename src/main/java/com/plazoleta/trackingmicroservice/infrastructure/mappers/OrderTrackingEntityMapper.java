package com.plazoleta.trackingmicroservice.infrastructure.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;
import com.plazoleta.trackingmicroservice.infrastructure.entities.OrderTrackingEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderTrackingEntityMapper {

    OrderTrackingEntity modelToEntity(OrderTrackingModel orderTracking);

    OrderTrackingModel entityToModel(OrderTrackingEntity entity);

    List<OrderTrackingModel> entityListToModelList(List<OrderTrackingEntity> entities);

    List<OrderTrackingEntity> modelListToEntityList(List<OrderTrackingModel> orderTrackings);
}
