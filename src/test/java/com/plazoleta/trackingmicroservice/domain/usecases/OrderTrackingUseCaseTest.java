package com.plazoleta.trackingmicroservice.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import com.plazoleta.trackingmicroservice.domain.model.OrderTrackingModel;
import com.plazoleta.trackingmicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.trackingmicroservice.domain.ports.out.OrderTrackingPersistencePort;
import com.plazoleta.trackingmicroservice.domain.utils.constants.DomainMessagesConstants;

@ExtendWith(MockitoExtension.class)
class OrderTrackingUseCaseTest {

    @Mock
    private OrderTrackingPersistencePort orderTrackingPersistencePort;

    @Mock
    private AuthenticatedUserPort authenticatedUserPort;

    @InjectMocks
    private OrderTrackingUseCase orderTrackingUseCase;

    private OrderTrackingModel validOrderTracking;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.of(2024, 3, 15, 10, 30);
        validOrderTracking = new OrderTrackingModel(
                12345L, // orderId
                67890L, // customerId
                "customer@plazoleta.com", // customerEmail
                OrderStatusEnum.PENDING, // previousStatus
                OrderStatusEnum.IN_PREPARATION, // currentStatus
                testDate, // date
                11111L, // employeeId
                "employee@plazoleta.com" // employeeEmail
        );
    }

    @Test
    void when_createOrderTrackingWithValidData_expect_trackingCreatedSuccessfully() {
        // Arrange
        OrderTrackingModel expectedSavedTracking = new OrderTrackingModel(
                validOrderTracking.getOrderId(),
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
                orderId, authenticatedUserId, "customer@plazoleta.com",
                OrderStatusEnum.PENDING, OrderStatusEnum.IN_PREPARATION,
                testDate, 11111L, "employee@plazoleta.com");

        OrderTrackingModel tracking2 = new OrderTrackingModel(
                orderId, authenticatedUserId, "customer@plazoleta.com",
                OrderStatusEnum.IN_PREPARATION, OrderStatusEnum.READY,
                testDate.plusHours(1), 11111L, "employee@plazoleta.com");

        List<OrderTrackingModel> trackingHistory = Arrays.asList(tracking1, tracking2);

        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        when(orderTrackingPersistencePort.findByOrderIdOrderByChangeDateAsc(orderId)).thenReturn(trackingHistory);

        // Act
        List<OrderTrackingModel> result = orderTrackingUseCase.getOrderTrackingHistory(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(tracking1.getCurrentStatus(), result.get(0).getCurrentStatus());
        assertEquals(tracking2.getCurrentStatus(), result.get(1).getCurrentStatus());
        verify(authenticatedUserPort).getAuthenticatedUserId();
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

        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        when(orderTrackingPersistencePort.findByOrderIdOrderByChangeDateAsc(orderId)).thenReturn(emptyTrackingHistory);

        // Act & Assert
        OrderTrackingNotFoundException exception = assertThrows(
                OrderTrackingNotFoundException.class,
                () -> orderTrackingUseCase.getOrderTrackingHistory(orderId));

        assertEquals(DomainMessagesConstants.ORDER_TRACKING_NOT_FOUND, exception.getMessage());
        verify(authenticatedUserPort).getAuthenticatedUserId();
        verify(orderTrackingPersistencePort).findByOrderIdOrderByChangeDateAsc(orderId);
    }

    @Test
    void when_getOrderTrackingHistoryForOrderBelongingToAnotherCustomer_expect_UnauthorizedOrderAccessException() {
        // Arrange
        Long orderId = 12345L;
        Long authenticatedUserId = 67890L;
        Long differentCustomerId = 99999L; // Different from authenticated user

        OrderTrackingModel tracking = new OrderTrackingModel(
                orderId, differentCustomerId, "other@plazoleta.com",
                OrderStatusEnum.PENDING, OrderStatusEnum.IN_PREPARATION,
                testDate, 11111L, "employee@plazoleta.com");

        List<OrderTrackingModel> trackingHistory = Arrays.asList(tracking);

        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        when(orderTrackingPersistencePort.findByOrderIdOrderByChangeDateAsc(orderId)).thenReturn(trackingHistory);

        // Act & Assert
        UnauthorizedOrderAccessException exception = assertThrows(
                UnauthorizedOrderAccessException.class,
                () -> orderTrackingUseCase.getOrderTrackingHistory(orderId));

        assertEquals(DomainMessagesConstants.ORDER_NOT_BELONGS_TO_CLIENT, exception.getMessage());
        verify(authenticatedUserPort).getAuthenticatedUserId();
        verify(orderTrackingPersistencePort).findByOrderIdOrderByChangeDateAsc(orderId);
    }

    @Test
    void when_getOrderTrackingHistoryForOwnOrder_expect_accessGranted() {
        // Arrange
        Long orderId = 12345L;
        Long authenticatedUserId = 67890L;

        OrderTrackingModel tracking = new OrderTrackingModel(
                orderId, authenticatedUserId, "customer@plazoleta.com",
                OrderStatusEnum.PENDING, OrderStatusEnum.IN_PREPARATION,
                testDate, 11111L, "employee@plazoleta.com");

        List<OrderTrackingModel> trackingHistory = Arrays.asList(tracking);

        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        when(orderTrackingPersistencePort.findByOrderIdOrderByChangeDateAsc(orderId)).thenReturn(trackingHistory);

        // Act
        List<OrderTrackingModel> result = orderTrackingUseCase.getOrderTrackingHistory(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(authenticatedUserId, result.get(0).getCustomerId());
        verify(authenticatedUserPort).getAuthenticatedUserId();
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
                12345L, 67890L, "customer@plazoleta.com",
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
}
