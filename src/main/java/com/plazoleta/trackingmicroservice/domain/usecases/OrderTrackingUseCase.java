package com.plazoleta.trackingmicroservice.domain.usecases;

import java.time.LocalDateTime;
import java.util.List;

import com.plazoleta.trackingmicroservice.domain.exceptions.OrderTrackingNotFoundException;
import com.plazoleta.trackingmicroservice.domain.exceptions.UnauthorizedOrderAccessException;
import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;
import com.plazoleta.trackingmicroservice.domain.ports.in.OrderTrackingServicePort;
import com.plazoleta.trackingmicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.trackingmicroservice.domain.ports.out.OrderTrackingPersistencePort;
import com.plazoleta.trackingmicroservice.domain.utils.constants.DomainMessagesConstants;

public class OrderTrackingUseCase implements OrderTrackingServicePort {

    private final OrderTrackingPersistencePort orderTrackingPersistencePort;
    private final AuthenticatedUserPort authenticatedUserPort;

    public OrderTrackingUseCase(OrderTrackingPersistencePort orderTrackingPersistencePort,
            AuthenticatedUserPort authenticatedUserPort) {
        this.orderTrackingPersistencePort = orderTrackingPersistencePort;
        this.authenticatedUserPort = authenticatedUserPort;
    }

    @Override
    public OrderTrackingModel createOrderTracking(OrderTrackingModel orderTracking) {
        if (orderTracking == null) {
            throw new IllegalArgumentException(DomainMessagesConstants.ORDER_TRACKING_INVALID_REQUEST);
        }

        if (orderTracking.getDate() == null) {
            orderTracking.setDate(LocalDateTime.now());
        }

        return orderTrackingPersistencePort.save(orderTracking);
    }

    @Override
    public List<OrderTrackingModel> getOrderTrackingHistory(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException(DomainMessagesConstants.ORDER_TRACKING_INVALID_ORDER_ID);
        }

        Long requestingCustomerId = authenticatedUserPort.getAuthenticatedUserId();

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
}
