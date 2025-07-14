package com.plazoleta.trackingmicroservice.infrastructure.repositories.mongo;

import com.plazoleta.trackingmicroservice.infrastructure.entities.OrderTrackingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MongoDB repository for order tracking entities
 * Provides data access operations for order tracking records
 */
@Repository
public interface OrderTrackingRepository extends MongoRepository<OrderTrackingEntity, String> {

    /**
     * Finds all tracking records for a specific order ordered by change date ascending
     * @param orderId the order ID
     * @return list of tracking records
     */
    List<OrderTrackingEntity> findByOrderIdOrderByChangeDateAsc(Long orderId);

    /**
     * Finds all tracking records for a specific customer ordered by change date descending
     * @param customerId the customer ID (as String in MongoDB)
     * @return list of tracking records
     */
    List<OrderTrackingEntity> findByCustomerIdOrderByChangeDateDesc(String customerId);

    /**
     * Finds the latest tracking record for a specific order
     * @param orderId the order ID
     * @return optional containing the latest tracking record
     */
    @Query(value = "{ 'order_id': ?0 }", sort = "{ 'change_date': -1 }")
    Optional<OrderTrackingEntity> findTopByOrderIdOrderByChangeDateDesc(Long orderId);

    /**
     * Checks if tracking records exist for a specific order
     * @param orderId the order ID
     * @return true if records exist
     */
    boolean existsByOrderId(Long orderId);
}
