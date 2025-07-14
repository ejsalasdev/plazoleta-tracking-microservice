package com.plazoleta.trackingmicroservice.infrastructure.adapters.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;
import com.plazoleta.trackingmicroservice.domain.ports.out.OrderTrackingPersistencePort;
import com.plazoleta.trackingmicroservice.infrastructure.entities.OrderTrackingEntity;
import com.plazoleta.trackingmicroservice.infrastructure.mappers.OrderTrackingEntityMapper;
import com.plazoleta.trackingmicroservice.infrastructure.repositories.mongo.OrderTrackingRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderTrackingPersistenceAdapter implements OrderTrackingPersistencePort {

    private static final Logger logger = LoggerFactory.getLogger(OrderTrackingPersistenceAdapter.class);
    
    private final OrderTrackingRepository mongoRepository;
    private final OrderTrackingEntityMapper mapper;

    @Override
    public OrderTrackingModel save(OrderTrackingModel orderTracking) {
        logger.info("Saving OrderTrackingModel: {}", orderTracking);
        
        OrderTrackingEntity entity = mapper.modelToEntity(orderTracking);
        logger.info("Mapped to OrderTrackingEntity: {}", entity);
        
        OrderTrackingEntity savedEntity = mongoRepository.save(entity);
        logger.info("Saved OrderTrackingEntity: {}", savedEntity);
        
        return mapper.entityToModel(savedEntity);
    }

    @Override
    public List<OrderTrackingModel> findByOrderIdOrderByChangeDateAsc(Long orderId) {
        List<OrderTrackingEntity> entities = mongoRepository.findByOrderIdOrderByDateAsc(orderId);
        return mapper.entityListToModelList(entities);
    }
}
