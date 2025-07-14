package com.plazoleta.trackingmicroservice.infrastructure.adapters.persistence;

import java.util.List;

import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;
import com.plazoleta.trackingmicroservice.domain.ports.out.OrderTrackingPersistencePort;
import com.plazoleta.trackingmicroservice.infrastructure.entities.OrderTrackingEntity;
import com.plazoleta.trackingmicroservice.infrastructure.mappers.OrderTrackingEntityMapper;
import com.plazoleta.trackingmicroservice.infrastructure.repositories.mongo.OrderTrackingRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderTrackingPersistenceAdapter implements OrderTrackingPersistencePort {

    private final OrderTrackingRepository mongoRepository;
    private final OrderTrackingEntityMapper mapper;

    @Override
    public OrderTrackingModel save(OrderTrackingModel orderTracking) {
        OrderTrackingEntity entity = mapper.modelToEntity(orderTracking);
        OrderTrackingEntity savedEntity = mongoRepository.save(entity);
        return mapper.entityToModel(savedEntity);
    }

    @Override
    public List<OrderTrackingModel> findByOrderIdOrderByChangeDateAsc(Long orderId) {
        List<OrderTrackingEntity> entities = mongoRepository.findByOrderIdOrderByChangeDateAsc(orderId);
        return mapper.entityListToModelList(entities);
    }
}
