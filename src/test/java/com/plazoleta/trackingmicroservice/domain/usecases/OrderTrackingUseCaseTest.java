package com.plazoleta.trackingmicroservice.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.plazoleta.trackingmicroservice.domain.enums.OrderStatusEnum;
import com.plazoleta.trackingmicroservice.domain.exceptions.InvalidOrderIdException;
import com.plazoleta.trackingmicroservice.domain.exceptions.InvalidOrderTrackingRequestException;
import com.plazoleta.trackingmicroservice.domain.exceptions.OrderTrackingNotFoundException;
import com.plazoleta.trackingmicroservice.domain.exceptions.UnauthorizedOrderAccessException;
import com.plazoleta.trackingmicroservice.domain.model.EmployeeEfficiencyModel;
import com.plazoleta.trackingmicroservice.domain.model.OrderEfficiencyModel;
import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;
import com.plazoleta.trackingmicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.trackingmicroservice.domain.ports.out.OrderTrackingPersistencePort;
import com.plazoleta.trackingmicroservice.domain.ports.out.external.RestaurantServicePort;
import com.plazoleta.trackingmicroservice.domain.utils.constants.DomainMessagesConstants;

@ExtendWith(MockitoExtension.class)
class OrderTrackingUseCaseTest {

    @Mock
    private OrderTrackingPersistencePort orderTrackingPersistencePort;

    @Mock
    private AuthenticatedUserPort authenticatedUserPort;

    @Mock
    private RestaurantServicePort restaurantServicePort;

    @InjectMocks
    private OrderTrackingUseCase orderTrackingUseCase;

    private OrderTrackingModel validOrderTracking;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.of(2024, 3, 15, 10, 30);
        validOrderTracking = new OrderTrackingModel(
                12345L,
                2L,
                67890L,
                "customer@plazoleta.com",
                OrderStatusEnum.PENDING,
                OrderStatusEnum.IN_PREPARATION,
                testDate,
                11111L,
                "employee@plazoleta.com");
    }

    @Test
    void when_createOrderTrackingWithValidData_expect_trackingCreatedSuccessfully() {
        // Arrange
        OrderTrackingModel expectedSavedTracking = new OrderTrackingModel(
                validOrderTracking.getOrderId(),
                validOrderTracking.getRestaurantId(),
                validOrderTracking.getCustomerId(),
                validOrderTracking.getCustomerEmail(),
                validOrderTracking.getPreviousStatus(),
                validOrderTracking.getCurrentStatus(),
                validOrderTracking.getDate(),
                validOrderTracking.getEmployeeId(),
                validOrderTracking.getEmployeeEmail());
        expectedSavedTracking.setId("60f3b4b3e4b0c4a7b8c9d1e2");

        when(orderTrackingPersistencePort.save(any(OrderTrackingModel.class))).thenReturn(expectedSavedTracking);

        // Act
        OrderTrackingModel result = orderTrackingUseCase.createOrderTracking(validOrderTracking);

        // Assert
        assertNotNull(result);
        assertEquals(expectedSavedTracking.getId(), result.getId());
        assertEquals(validOrderTracking.getOrderId(), result.getOrderId());
        assertEquals(validOrderTracking.getCustomerId(), result.getCustomerId());
        assertEquals(validOrderTracking.getCurrentStatus(), result.getCurrentStatus());
        verify(orderTrackingPersistencePort).save(validOrderTracking);
    }

    @Test
    void when_createOrderTrackingWithNullDate_expect_dateSetToCurrentTime() {
        // Arrange
        validOrderTracking.setDate(null);
        OrderTrackingModel savedTracking = new OrderTrackingModel();
        savedTracking.setId("60f3b4b3e4b0c4a7b8c9d1e2");

        when(orderTrackingPersistencePort.save(any(OrderTrackingModel.class))).thenReturn(savedTracking);

        // Act
        OrderTrackingModel result = orderTrackingUseCase.createOrderTracking(validOrderTracking);

        // Assert
        assertNotNull(result);
        assertNotNull(validOrderTracking.getDate()); // Date should be set by the use case
        verify(orderTrackingPersistencePort).save(validOrderTracking);
    }

    @Test
    void when_createOrderTrackingWithNullRequest_expect_InvalidOrderTrackingRequestException() {
        // Arrange
        OrderTrackingModel nullRequest = null;

        // Act & Assert
        InvalidOrderTrackingRequestException exception = assertThrows(
                InvalidOrderTrackingRequestException.class,
                () -> orderTrackingUseCase.createOrderTracking(nullRequest));

        assertEquals(DomainMessagesConstants.ORDER_TRACKING_INVALID_REQUEST, exception.getMessage());
    }

    @Test
    void when_getOrderTrackingHistoryWithValidOrderId_expect_trackingHistoryReturned() {
        // Arrange
        Long orderId = 12345L;
        Long authenticatedUserId = 67890L;

        OrderTrackingModel tracking1 = new OrderTrackingModel(
                orderId, validOrderTracking.getRestaurantId(), authenticatedUserId, "customer@plazoleta.com",
                OrderStatusEnum.PENDING, OrderStatusEnum.IN_PREPARATION,
                testDate, 11111L, "employee@plazoleta.com");

        OrderTrackingModel tracking2 = new OrderTrackingModel(
                orderId, validOrderTracking.getRestaurantId(), authenticatedUserId, "customer@plazoleta.com",
                OrderStatusEnum.IN_PREPARATION, OrderStatusEnum.READY,
                testDate.plusHours(1), 11111L, "employee@plazoleta.com");

        List<OrderTrackingModel> trackingHistory = Arrays.asList(tracking1, tracking2);

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(authenticatedUserId);
        when(orderTrackingPersistencePort.findByOrderIdOrderByChangeDateAsc(orderId)).thenReturn(trackingHistory);

        // Act
        List<OrderTrackingModel> result = orderTrackingUseCase.getOrderTrackingHistory(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(tracking1.getCurrentStatus(), result.get(0).getCurrentStatus());
        assertEquals(tracking2.getCurrentStatus(), result.get(1).getCurrentStatus());
        verify(authenticatedUserPort).getCurrentUserId();
        verify(orderTrackingPersistencePort).findByOrderIdOrderByChangeDateAsc(orderId);
    }

    @Test
    void when_getOrderTrackingHistoryWithNullOrderId_expect_InvalidOrderIdException() {
        // Arrange
        Long nullOrderId = null;

        // Act & Assert
        InvalidOrderIdException exception = assertThrows(
                InvalidOrderIdException.class,
                () -> orderTrackingUseCase.getOrderTrackingHistory(nullOrderId));

        assertEquals(DomainMessagesConstants.ORDER_TRACKING_INVALID_ORDER_ID, exception.getMessage());
    }

    @Test
    void when_getOrderTrackingHistoryForNonExistentOrder_expect_OrderTrackingNotFoundException() {
        // Arrange
        Long orderId = 99999L;
        Long authenticatedUserId = 67890L;
        List<OrderTrackingModel> emptyTrackingHistory = Collections.emptyList();

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(authenticatedUserId);
        when(orderTrackingPersistencePort.findByOrderIdOrderByChangeDateAsc(orderId)).thenReturn(emptyTrackingHistory);

        // Act & Assert
        OrderTrackingNotFoundException exception = assertThrows(
                OrderTrackingNotFoundException.class,
                () -> orderTrackingUseCase.getOrderTrackingHistory(orderId));

        assertEquals(DomainMessagesConstants.ORDER_TRACKING_NOT_FOUND, exception.getMessage());
        verify(authenticatedUserPort).getCurrentUserId();
        verify(orderTrackingPersistencePort).findByOrderIdOrderByChangeDateAsc(orderId);
    }

    @Test
    void when_getOrderTrackingHistoryForOrderBelongingToAnotherCustomer_expect_UnauthorizedOrderAccessException() {
        // Arrange
        Long orderId = 12345L;
        Long authenticatedUserId = 67890L;
        Long differentCustomerId = 99999L; // Different from authenticated user

        OrderTrackingModel tracking = new OrderTrackingModel(
                orderId, validOrderTracking.getRestaurantId(), differentCustomerId, "other@plazoleta.com",
                OrderStatusEnum.PENDING, OrderStatusEnum.IN_PREPARATION,
                testDate, 11111L, "employee@plazoleta.com");

        List<OrderTrackingModel> trackingHistory = Arrays.asList(tracking);

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(authenticatedUserId);
        when(orderTrackingPersistencePort.findByOrderIdOrderByChangeDateAsc(orderId)).thenReturn(trackingHistory);

        // Act & Assert
        UnauthorizedOrderAccessException exception = assertThrows(
                UnauthorizedOrderAccessException.class,
                () -> orderTrackingUseCase.getOrderTrackingHistory(orderId));

        assertEquals(DomainMessagesConstants.ORDER_NOT_BELONGS_TO_CLIENT, exception.getMessage());
        verify(authenticatedUserPort).getCurrentUserId();
        verify(orderTrackingPersistencePort).findByOrderIdOrderByChangeDateAsc(orderId);
    }

    @Test
    void when_getOrderTrackingHistoryForOwnOrder_expect_accessGranted() {
        // Arrange
        Long orderId = 12345L;
        Long authenticatedUserId = 67890L;

        OrderTrackingModel tracking = new OrderTrackingModel(
                orderId, validOrderTracking.getRestaurantId(), authenticatedUserId, "customer@plazoleta.com",
                OrderStatusEnum.PENDING, OrderStatusEnum.IN_PREPARATION,
                testDate, 11111L, "employee@plazoleta.com");

        List<OrderTrackingModel> trackingHistory = Arrays.asList(tracking);

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(authenticatedUserId);
        when(orderTrackingPersistencePort.findByOrderIdOrderByChangeDateAsc(orderId)).thenReturn(trackingHistory);

        // Act
        List<OrderTrackingModel> result = orderTrackingUseCase.getOrderTrackingHistory(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(authenticatedUserId, result.get(0).getCustomerId());
        verify(authenticatedUserPort).getCurrentUserId();
        verify(orderTrackingPersistencePort).findByOrderIdOrderByChangeDateAsc(orderId);
    }

    @Test
    void when_createOrderTrackingWithExistingDate_expect_datePreserved() {
        // Arrange
        LocalDateTime specificDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        validOrderTracking.setDate(specificDate);

        OrderTrackingModel savedTracking = new OrderTrackingModel();
        savedTracking.setDate(specificDate);

        when(orderTrackingPersistencePort.save(any(OrderTrackingModel.class))).thenReturn(savedTracking);

        // Act
        orderTrackingUseCase.createOrderTracking(validOrderTracking);

        // Assert
        assertEquals(specificDate, validOrderTracking.getDate()); // Original date should be preserved
        verify(orderTrackingPersistencePort).save(validOrderTracking);
    }

    @Test
    void when_createOrderTrackingWithAllRequiredFields_expect_successfulSave() {
        // Arrange
        OrderTrackingModel completeTracking = new OrderTrackingModel(
                12345L, validOrderTracking.getRestaurantId(), 67890L, "customer@plazoleta.com",
                OrderStatusEnum.PENDING, OrderStatusEnum.IN_PREPARATION,
                testDate, 11111L, "employee@plazoleta.com");

        when(orderTrackingPersistencePort.save(any(OrderTrackingModel.class))).thenReturn(completeTracking);

        // Act
        OrderTrackingModel result = orderTrackingUseCase.createOrderTracking(completeTracking);

        // Assert
        assertNotNull(result);
        assertEquals(completeTracking.getOrderId(), result.getOrderId());
        assertEquals(completeTracking.getCustomerId(), result.getCustomerId());
        assertEquals(completeTracking.getPreviousStatus(), result.getPreviousStatus());
        assertEquals(completeTracking.getCurrentStatus(), result.getCurrentStatus());
        verify(orderTrackingPersistencePort).save(completeTracking);
    }

    @Test
    void when_calculateOrderEfficiencyWithValidRestaurantId_expect_efficiencyCalculated() {
        // Arrange
        Long restaurantId = 2L;
        Long ownerId = 1L;
        LocalDateTime pendingTime = LocalDateTime.of(2024, 3, 15, 10, 0);
        LocalDateTime deliveredTime = LocalDateTime.of(2024, 3, 15, 10, 30);

        List<OrderTrackingModel> trackingData = Arrays.asList(
                createOrderTracking(1001L, 201L, OrderStatusEnum.PENDING, pendingTime, 301L, restaurantId),
                createOrderTracking(1001L, 201L, OrderStatusEnum.DELIVERED, deliveredTime, 301L, restaurantId));

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(ownerId);
        when(restaurantServicePort.getRestaurantIdByOwnerId(ownerId)).thenReturn(Optional.of(restaurantId));
        when(orderTrackingPersistencePort.findByRestaurantIdOrderByChangeDateAsc(restaurantId))
                .thenReturn(trackingData);

        // Act
        List<OrderEfficiencyModel> result = orderTrackingUseCase.calculateOrderEfficiency(restaurantId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        OrderEfficiencyModel efficiency = result.get(0);
        assertEquals(1001L, efficiency.getOrderId());
        assertEquals(pendingTime, efficiency.getStartTime());
        assertEquals(deliveredTime, efficiency.getEndTime());
        assertEquals(Duration.ofMinutes(30), efficiency.getDuration());
    }

    @Test
    void when_calculateOrderEfficiencyWithNullRestaurantId_expect_InvalidOrderIdException() {
        // Act & Assert
        InvalidOrderIdException exception = assertThrows(
                InvalidOrderIdException.class,
                () -> orderTrackingUseCase.calculateOrderEfficiency(null));

        assertEquals(DomainMessagesConstants.ORDER_TRACKING_INVALID_RESTAURANT_ID, exception.getMessage());
    }

    @Test
    void when_calculateOrderEfficiencyWithUnauthorizedOwner_expect_UnauthorizedOrderAccessException() {
        // Arrange
        Long restaurantId = 2L;
        Long ownerId = 1L;
        Long differentRestaurantId = 3L;

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(ownerId);
        when(restaurantServicePort.getRestaurantIdByOwnerId(ownerId)).thenReturn(Optional.of(differentRestaurantId));

        // Act & Assert
        UnauthorizedOrderAccessException exception = assertThrows(
                UnauthorizedOrderAccessException.class,
                () -> orderTrackingUseCase.calculateOrderEfficiency(restaurantId));

        assertEquals(DomainMessagesConstants.RESTAURANT_NOT_BELONGS_TO_OWNER, exception.getMessage());
    }

    @Test
    void when_calculateOrderEfficiencyWithNoRestaurantForOwner_expect_UnauthorizedOrderAccessException() {
        // Arrange
        Long restaurantId = 2L;
        Long ownerId = 1L;

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(ownerId);
        when(restaurantServicePort.getRestaurantIdByOwnerId(ownerId)).thenReturn(Optional.empty());

        // Act & Assert
        UnauthorizedOrderAccessException exception = assertThrows(
                UnauthorizedOrderAccessException.class,
                () -> orderTrackingUseCase.calculateOrderEfficiency(restaurantId));

        assertEquals(DomainMessagesConstants.RESTAURANT_NOT_BELONGS_TO_OWNER, exception.getMessage());
    }

    @Test
    void when_calculateEmployeeEfficiencyWithValidData_expect_averageTimeCalculated() {
        // Arrange
        Long restaurantId = 2L;
        Long ownerId = 1L;
        Long employee1Id = 301L;
        Long employee2Id = 302L;

        List<OrderTrackingModel> trackingData = Arrays.asList(
                // Employee 301 - Order 1001 (30 minutes)
                createOrderTracking(1001L, 201L, OrderStatusEnum.PENDING, LocalDateTime.of(2024, 3, 15, 10, 0),
                        employee1Id, restaurantId),
                createOrderTracking(1001L, 201L, OrderStatusEnum.DELIVERED, LocalDateTime.of(2024, 3, 15, 10, 30),
                        employee1Id, restaurantId),

                // Employee 301 - Order 1002 (20 minutes)
                createOrderTracking(1002L, 202L, OrderStatusEnum.PENDING, LocalDateTime.of(2024, 3, 15, 11, 0),
                        employee1Id, restaurantId),
                createOrderTracking(1002L, 202L, OrderStatusEnum.DELIVERED, LocalDateTime.of(2024, 3, 15, 11, 20),
                        employee1Id, restaurantId),

                // Employee 302 - Order 1003 (40 minutes)
                createOrderTracking(1003L, 203L, OrderStatusEnum.PENDING, LocalDateTime.of(2024, 3, 15, 12, 0),
                        employee2Id, restaurantId),
                createOrderTracking(1003L, 203L, OrderStatusEnum.DELIVERED, LocalDateTime.of(2024, 3, 15, 12, 40),
                        employee2Id, restaurantId));

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(ownerId);
        when(restaurantServicePort.getRestaurantIdByOwnerId(ownerId)).thenReturn(Optional.of(restaurantId));
        when(orderTrackingPersistencePort.findByRestaurantIdOrderByChangeDateAsc(restaurantId))
                .thenReturn(trackingData);

        // Act
        List<EmployeeEfficiencyModel> result = orderTrackingUseCase.calculateEmployeeEfficiency(restaurantId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Find employee 301 result (average 25 minutes from 30 and 20)
        EmployeeEfficiencyModel emp1 = result.stream()
                .filter(e -> e.getEmployeeId().equals(employee1Id))
                .findFirst()
                .orElse(null);
        assertNotNull(emp1);
        assertEquals(2, emp1.getCompletedOrders());
        assertEquals(Duration.ofMinutes(25), emp1.getAverageDuration());

        // Find employee 302 result (40 minutes from 1 order)
        EmployeeEfficiencyModel emp2 = result.stream()
                .filter(e -> e.getEmployeeId().equals(employee2Id))
                .findFirst()
                .orElse(null);
        assertNotNull(emp2);
        assertEquals(1, emp2.getCompletedOrders());
        assertEquals(Duration.ofMinutes(40), emp2.getAverageDuration());
    }

    @Test
    void when_calculateEmployeeEfficiencyWithNullRestaurantId_expect_InvalidOrderIdException() {
        // Act & Assert
        InvalidOrderIdException exception = assertThrows(
                InvalidOrderIdException.class,
                () -> orderTrackingUseCase.calculateEmployeeEfficiency(null));

        assertEquals(DomainMessagesConstants.ORDER_TRACKING_INVALID_RESTAURANT_ID, exception.getMessage());
    }

    @Test
    void when_calculateEmployeeEfficiencyWithUnauthorizedOwner_expect_UnauthorizedOrderAccessException() {
        // Arrange
        Long restaurantId = 2L;
        Long ownerId = 1L;
        Long differentRestaurantId = 3L;

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(ownerId);
        when(restaurantServicePort.getRestaurantIdByOwnerId(ownerId)).thenReturn(Optional.of(differentRestaurantId));

        // Act & Assert
        UnauthorizedOrderAccessException exception = assertThrows(
                UnauthorizedOrderAccessException.class,
                () -> orderTrackingUseCase.calculateEmployeeEfficiency(restaurantId));

        assertEquals(DomainMessagesConstants.RESTAURANT_NOT_BELONGS_TO_OWNER, exception.getMessage());
    }

    @Test
    void when_calculateEmployeeEfficiencyWithNoEmployeeData_expect_emptyResult() {
        // Arrange
        Long restaurantId = 2L;
        Long ownerId = 1L;

        List<OrderTrackingModel> trackingData = Arrays.asList(
                createOrderTracking(1001L, 201L, OrderStatusEnum.PENDING, LocalDateTime.of(2024, 3, 15, 10, 0), null,
                        restaurantId),
                createOrderTracking(1001L, 201L, OrderStatusEnum.DELIVERED, LocalDateTime.of(2024, 3, 15, 10, 30), null,
                        restaurantId));

        when(authenticatedUserPort.getCurrentUserId()).thenReturn(ownerId);
        when(restaurantServicePort.getRestaurantIdByOwnerId(ownerId)).thenReturn(Optional.of(restaurantId));
        when(orderTrackingPersistencePort.findByRestaurantIdOrderByChangeDateAsc(restaurantId))
                .thenReturn(trackingData);

        // Act
        List<EmployeeEfficiencyModel> result = orderTrackingUseCase.calculateEmployeeEfficiency(restaurantId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private OrderTrackingModel createOrderTracking(Long orderId, Long customerId, OrderStatusEnum status,
            LocalDateTime date, Long employeeId, Long restaurantId) {
        OrderTrackingModel tracking = new OrderTrackingModel();
        tracking.setOrderId(orderId);
        tracking.setCustomerId(customerId);
        tracking.setCurrentStatus(status);
        tracking.setDate(date);
        tracking.setEmployeeId(employeeId);
        tracking.setRestaurantId(restaurantId);
        return tracking;
    }
}
