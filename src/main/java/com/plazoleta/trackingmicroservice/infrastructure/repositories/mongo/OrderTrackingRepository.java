package com.plazoleta.trackingmicroservice.infrastructure.repositories.mongo;

import com.plazoleta.trackingmicroservice.infrastructure.entities.OrderTrackingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderTrackingRepository extends MongoRepository<OrderTrackingEntity, String> {

    
    List<OrderTrackingEntity> findByOrderIdOrderByDateAsc(Long orderId);

    
    List<OrderTrackingEntity> findByCustomerIdOrderByDateDesc(String customerId);

    
    @Query(value = "{ 'orderId': ?0 }", sort = "{ 'date': -1 }")
    Optional<OrderTrackingEntity> findTopByOrderIdOrderByDateDesc(Long orderId);

    boolean existsByOrderId(Long orderId);
}
