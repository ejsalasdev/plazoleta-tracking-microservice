package com.plazoleta.trackingmicroservice.domain.usecases;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.plazoleta.trackingmicroservice.domain.enums.OrderStatusEnum;
import com.plazoleta.trackingmicroservice.domain.exceptions.InvalidOrderIdException;
import com.plazoleta.trackingmicroservice.domain.exceptions.InvalidOrderTrackingRequestException;
import com.plazoleta.trackingmicroservice.domain.exceptions.OrderTrackingNotFoundException;
import com.plazoleta.trackingmicroservice.domain.exceptions.UnauthorizedOrderAccessException;
import com.plazoleta.trackingmicroservice.domain.model.OrderEfficiencyModel;
import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;
import com.plazoleta.trackingmicroservice.domain.ports.in.OrderTrackingServicePort;
import com.plazoleta.trackingmicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.trackingmicroservice.domain.ports.out.OrderTrackingPersistencePort;
import com.plazoleta.trackingmicroservice.domain.ports.out.external.RestaurantServicePort;
import com.plazoleta.trackingmicroservice.domain.utils.constants.DomainMessagesConstants;

public class OrderTrackingUseCase implements OrderTrackingServicePort {

    private final OrderTrackingPersistencePort orderTrackingPersistencePort;
    private final AuthenticatedUserPort authenticatedUserPort;
    private final RestaurantServicePort restaurantServicePort;

    public OrderTrackingUseCase(OrderTrackingPersistencePort orderTrackingPersistencePort,
            AuthenticatedUserPort authenticatedUserPort, RestaurantServicePort restaurantServicePort) {
        this.orderTrackingPersistencePort = orderTrackingPersistencePort;
        this.authenticatedUserPort = authenticatedUserPort;
        this.restaurantServicePort = restaurantServicePort;
    }

    @Override
    public OrderTrackingModel createOrderTracking(OrderTrackingModel orderTracking) {
        if (orderTracking == null) {
            throw new InvalidOrderTrackingRequestException(DomainMessagesConstants.ORDER_TRACKING_INVALID_REQUEST);
        }

        if (orderTracking.getDate() == null) {
            orderTracking.setDate(LocalDateTime.now());
        }

        return orderTrackingPersistencePort.save(orderTracking);
    }

    @Override
    public List<OrderTrackingModel> getOrderTrackingHistory(Long orderId) {
        if (orderId == null) {
            throw new InvalidOrderIdException(DomainMessagesConstants.ORDER_TRACKING_INVALID_ORDER_ID);
        }

        Long requestingCustomerId = authenticatedUserPort.getCurrentUserId();

        List<OrderTrackingModel> trackingHistory = orderTrackingPersistencePort
                .findByOrderIdOrderByChangeDateAsc(orderId);

        if (trackingHistory.isEmpty()) {
            throw new OrderTrackingNotFoundException(DomainMessagesConstants.ORDER_TRACKING_NOT_FOUND);
        }

        OrderTrackingModel firstRecord = trackingHistory.get(0);
        if (!firstRecord.getCustomerId().equals(requestingCustomerId)) {
            throw new UnauthorizedOrderAccessException(DomainMessagesConstants.ORDER_NOT_BELONGS_TO_CLIENT);
        }

        return trackingHistory;
    }

    @Override
    public List<OrderEfficiencyModel> calculateOrderEfficiency(Long restaurantId) {
        if (restaurantId == null) {
            throw new InvalidOrderIdException(DomainMessagesConstants.ORDER_TRACKING_INVALID_RESTAURANT_ID);
        }

        Long currentOwnerId = authenticatedUserPort.getCurrentUserId();

        Optional<Long> requestingRestaurantId = restaurantServicePort.getRestaurantIdByOwnerId(currentOwnerId);

        if (requestingRestaurantId.isEmpty() || !requestingRestaurantId.get().equals(restaurantId)) {
            throw new UnauthorizedOrderAccessException(DomainMessagesConstants.RESTAURANT_NOT_BELONGS_TO_OWNER);
        }

        List<OrderTrackingModel> trackingData = orderTrackingPersistencePort.findByRestaurantIdOrderByChangeDateAsc(restaurantId);
        
        return calculateEfficiencyFromTrackingData(trackingData);
    }

    private List<OrderEfficiencyModel> calculateEfficiencyFromTrackingData(List<OrderTrackingModel> trackingModels) {
        // Agrupar por orderId
        Map<Long, List<OrderTrackingModel>> orderGroups = trackingModels.stream()
                .collect(Collectors.groupingBy(OrderTrackingModel::getOrderId));

        return orderGroups.entrySet().stream()
                .map(entry -> processOrderEfficiency(entry.getKey(), entry.getValue()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<OrderEfficiencyModel> processOrderEfficiency(Long orderId, List<OrderTrackingModel> trackingList) {
        // Buscar el primer estado (cuando pasa a PENDING)
        Optional<LocalDateTime> startTime = trackingList.stream()
                .filter(t -> t.getCurrentStatus() == OrderStatusEnum.PENDING)
                .map(OrderTrackingModel::getDate)
                .min(LocalDateTime::compareTo);

        // Buscar cuando pasa a DELIVERED (terminado)
        Optional<LocalDateTime> endTime = trackingList.stream()
                .filter(t -> t.getCurrentStatus() == OrderStatusEnum.DELIVERED)
                .map(OrderTrackingModel::getDate)
                .max(LocalDateTime::compareTo);

        // Solo procesar si tiene inicio y fin
        if (startTime.isPresent() && endTime.isPresent()) {
            Duration duration = Duration.between(startTime.get(), endTime.get());

            return Optional.of(new OrderEfficiencyModel(
                    orderId,
                    startTime.get(),
                    endTime.get(),
                    duration
            ));
        }

        return Optional.empty();
    }
}
