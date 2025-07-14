package com.plazoleta.trackingmicroservice.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.plazoleta.trackingmicroservice.domain.enums.OrderStatusEnum;

class OrderTrackingModelTest {

    private OrderTrackingModel orderTrackingModel;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.of(2024, 3, 15, 10, 30);
        orderTrackingModel = new OrderTrackingModel();
    }

    @Test
    void when_createOrderTrackingModelWithDefaultConstructor_expect_objectCreatedWithNullValues() {
        // Arrange & Act
        OrderTrackingModel model = new OrderTrackingModel();

        // Assert
        assertNotNull(model);
        assertNull(model.getId());
        assertNull(model.getOrderId());
        assertNull(model.getCustomerId());
        assertNull(model.getCustomerEmail());
        assertNull(model.getDate());
        assertNull(model.getPreviousStatus());
        assertNull(model.getCurrentStatus());
        assertNull(model.getEmployeeId());
        assertNull(model.getEmployeeEmail());
    }

    @Test
    void when_createOrderTrackingModelWithParameterizedConstructor_expect_objectCreatedWithCorrectValues() {
        // Arrange
        Long orderId = 12345L;
        Long customerId = 67890L;
        String customerEmail = "customer@plazoleta.com";
        OrderStatusEnum previousStatus = OrderStatusEnum.PENDING;
        OrderStatusEnum currentStatus = OrderStatusEnum.IN_PREPARATION;
        Long employeeId = 11111L;
        String employeeEmail = "employee@plazoleta.com";

        // Act
        OrderTrackingModel model = new OrderTrackingModel(
                orderId, customerId, customerEmail,
                previousStatus, currentStatus, testDate,
                employeeId, employeeEmail);

        // Assert
        assertNotNull(model);
        assertEquals(orderId, model.getOrderId());
        assertEquals(customerId, model.getCustomerId());
        assertEquals(customerEmail, model.getCustomerEmail());
        assertEquals(previousStatus, model.getPreviousStatus());
        assertEquals(currentStatus, model.getCurrentStatus());
        assertEquals(testDate, model.getDate());
        assertEquals(employeeId, model.getEmployeeId());
        assertEquals(employeeEmail, model.getEmployeeEmail());
        assertNull(model.getId()); // ID is not set in constructor
    }

    @Test
    void when_setAndGetId_expect_correctValueStored() {
        // Arrange
        String expectedId = "60f3b4b3e4b0c4a7b8c9d1e2";

        // Act
        orderTrackingModel.setId(expectedId);

        // Assert
        assertEquals(expectedId, orderTrackingModel.getId());
    }

    @Test
    void when_setAndGetOrderId_expect_correctValueStored() {
        // Arrange
        Long expectedOrderId = 12345L;

        // Act
        orderTrackingModel.setOrderId(expectedOrderId);

        // Assert
        assertEquals(expectedOrderId, orderTrackingModel.getOrderId());
    }

    @Test
    void when_setAndGetCustomerId_expect_correctValueStored() {
        // Arrange
        Long expectedCustomerId = 67890L;

        // Act
        orderTrackingModel.setCustomerId(expectedCustomerId);

        // Assert
        assertEquals(expectedCustomerId, orderTrackingModel.getCustomerId());
    }

    @Test
    void when_setAndGetCustomerEmail_expect_correctValueStored() {
        // Arrange
        String expectedEmail = "customer@plazoleta.com";

        // Act
        orderTrackingModel.setCustomerEmail(expectedEmail);

        // Assert
        assertEquals(expectedEmail, orderTrackingModel.getCustomerEmail());
    }

    @Test
    void when_setAndGetDate_expect_correctValueStored() {
        // Arrange
        LocalDateTime expectedDate = LocalDateTime.of(2024, 1, 1, 12, 0);

        // Act
        orderTrackingModel.setDate(expectedDate);

        // Assert
        assertEquals(expectedDate, orderTrackingModel.getDate());
    }

    @Test
    void when_setAndGetPreviousStatus_expect_correctValueStored() {
        // Arrange
        OrderStatusEnum expectedStatus = OrderStatusEnum.PENDING;

        // Act
        orderTrackingModel.setPreviousStatus(expectedStatus);

        // Assert
        assertEquals(expectedStatus, orderTrackingModel.getPreviousStatus());
    }

    @Test
    void when_setAndGetCurrentStatus_expect_correctValueStored() {
        // Arrange
        OrderStatusEnum expectedStatus = OrderStatusEnum.IN_PREPARATION;

        // Act
        orderTrackingModel.setCurrentStatus(expectedStatus);

        // Assert
        assertEquals(expectedStatus, orderTrackingModel.getCurrentStatus());
    }

    @Test
    void when_setAndGetEmployeeId_expect_correctValueStored() {
        // Arrange
        Long expectedEmployeeId = 11111L;

        // Act
        orderTrackingModel.setEmployeeId(expectedEmployeeId);

        // Assert
        assertEquals(expectedEmployeeId, orderTrackingModel.getEmployeeId());
    }

    @Test
    void when_setAndGetEmployeeEmail_expect_correctValueStored() {
        // Arrange
        String expectedEmail = "employee@plazoleta.com";

        // Act
        orderTrackingModel.setEmployeeEmail(expectedEmail);

        // Assert
        assertEquals(expectedEmail, orderTrackingModel.getEmployeeEmail());
    }

    @Test
    void when_setAllPropertiesSequentially_expect_allValuesCorrectlyStored() {
        // Arrange
        String id = "60f3b4b3e4b0c4a7b8c9d1e2";
        Long orderId = 12345L;
        Long customerId = 67890L;
        String customerEmail = "customer@plazoleta.com";
        OrderStatusEnum previousStatus = OrderStatusEnum.PENDING;
        OrderStatusEnum currentStatus = OrderStatusEnum.IN_PREPARATION;
        Long employeeId = 11111L;
        String employeeEmail = "employee@plazoleta.com";

        // Act
        orderTrackingModel.setId(id);
        orderTrackingModel.setOrderId(orderId);
        orderTrackingModel.setCustomerId(customerId);
        orderTrackingModel.setCustomerEmail(customerEmail);
        orderTrackingModel.setDate(testDate);
        orderTrackingModel.setPreviousStatus(previousStatus);
        orderTrackingModel.setCurrentStatus(currentStatus);
        orderTrackingModel.setEmployeeId(employeeId);
        orderTrackingModel.setEmployeeEmail(employeeEmail);

        // Assert
        assertEquals(id, orderTrackingModel.getId());
        assertEquals(orderId, orderTrackingModel.getOrderId());
        assertEquals(customerId, orderTrackingModel.getCustomerId());
        assertEquals(customerEmail, orderTrackingModel.getCustomerEmail());
        assertEquals(testDate, orderTrackingModel.getDate());
        assertEquals(previousStatus, orderTrackingModel.getPreviousStatus());
        assertEquals(currentStatus, orderTrackingModel.getCurrentStatus());
        assertEquals(employeeId, orderTrackingModel.getEmployeeId());
        assertEquals(employeeEmail, orderTrackingModel.getEmployeeEmail());
    }

    @Test
    void when_setNullValues_expect_nullValuesStored() {
        // Arrange & Act
        orderTrackingModel.setId(null);
        orderTrackingModel.setOrderId(null);
        orderTrackingModel.setCustomerId(null);
        orderTrackingModel.setCustomerEmail(null);
        orderTrackingModel.setDate(null);
        orderTrackingModel.setPreviousStatus(null);
        orderTrackingModel.setCurrentStatus(null);
        orderTrackingModel.setEmployeeId(null);
        orderTrackingModel.setEmployeeEmail(null);

        // Assert
        assertNull(orderTrackingModel.getId());
        assertNull(orderTrackingModel.getOrderId());
        assertNull(orderTrackingModel.getCustomerId());
        assertNull(orderTrackingModel.getCustomerEmail());
        assertNull(orderTrackingModel.getDate());
        assertNull(orderTrackingModel.getPreviousStatus());
        assertNull(orderTrackingModel.getCurrentStatus());
        assertNull(orderTrackingModel.getEmployeeId());
        assertNull(orderTrackingModel.getEmployeeEmail());
    }

    // ========== EDGE CASES AND BOUNDARY TESTS ==========

    @Test
    void when_createModelWithAllOrderStatuses_expect_correctStatusesStored() {
        // Arrange & Act & Assert
        for (OrderStatusEnum status : OrderStatusEnum.values()) {
            OrderTrackingModel model = new OrderTrackingModel();
            model.setPreviousStatus(status);
            model.setCurrentStatus(status);

            assertEquals(status, model.getPreviousStatus());
            assertEquals(status, model.getCurrentStatus());
        }
    }

    @Test
    void when_setEmptyStringsForEmailFields_expect_emptyStringsStored() {
        // Arrange
        String emptyString = "";

        // Act
        orderTrackingModel.setCustomerEmail(emptyString);
        orderTrackingModel.setEmployeeEmail(emptyString);

        // Assert
        assertEquals(emptyString, orderTrackingModel.getCustomerEmail());
        assertEquals(emptyString, orderTrackingModel.getEmployeeEmail());
    }

    @Test
    void when_setValidEmailFormats_expect_emailsStoredCorrectly() {
        // Arrange
        String customerEmail = "customer@plazoleta.com";
        String employeeEmail = "employee@restaurant.co.uk";

        // Act
        orderTrackingModel.setCustomerEmail(customerEmail);
        orderTrackingModel.setEmployeeEmail(employeeEmail);

        // Assert
        assertEquals(customerEmail, orderTrackingModel.getCustomerEmail());
        assertEquals(employeeEmail, orderTrackingModel.getEmployeeEmail());
    }

    @Test
    void when_setDateToCurrentTime_expect_currentTimeStored() {
        // Arrange
        LocalDateTime currentTime = LocalDateTime.now();

        // Act
        orderTrackingModel.setDate(currentTime);

        // Assert
        assertEquals(currentTime, orderTrackingModel.getDate());
    }

    @Test
    void when_setLargeIdValues_expect_valuesStoredCorrectly() {
        // Arrange
        Long largeOrderId = Long.MAX_VALUE;
        Long largeCustomerId = Long.MAX_VALUE - 1;
        Long largeEmployeeId = Long.MAX_VALUE - 2;

        // Act
        orderTrackingModel.setOrderId(largeOrderId);
        orderTrackingModel.setCustomerId(largeCustomerId);
        orderTrackingModel.setEmployeeId(largeEmployeeId);

        // Assert
        assertEquals(largeOrderId, orderTrackingModel.getOrderId());
        assertEquals(largeCustomerId, orderTrackingModel.getCustomerId());
        assertEquals(largeEmployeeId, orderTrackingModel.getEmployeeId());
    }

    @Test
    void when_overwriteExistingValues_expect_newValuesStored() {
        // Arrange
        orderTrackingModel.setOrderId(12345L);
        orderTrackingModel.setCustomerId(67890L);
        orderTrackingModel.setCurrentStatus(OrderStatusEnum.PENDING);

        Long newOrderId = 54321L;
        Long newCustomerId = 98765L;
        OrderStatusEnum newStatus = OrderStatusEnum.DELIVERED;

        // Act
        orderTrackingModel.setOrderId(newOrderId);
        orderTrackingModel.setCustomerId(newCustomerId);
        orderTrackingModel.setCurrentStatus(newStatus);

        // Assert
        assertEquals(newOrderId, orderTrackingModel.getOrderId());
        assertEquals(newCustomerId, orderTrackingModel.getCustomerId());
        assertEquals(newStatus, orderTrackingModel.getCurrentStatus());
    }
}
