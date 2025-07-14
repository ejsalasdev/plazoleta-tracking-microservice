package com.plazoleta.trackingmicroservice.domain.model;

import java.time.LocalDateTime;

import com.plazoleta.trackingmicroservice.domain.enums.OrderStatusEnum;

public class OrderTrackingModel {

    private String id;
    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private LocalDateTime date;
    private OrderStatusEnum previousStatus;
    private OrderStatusEnum currentStatus;
    private Long employeeId;
    private String employeeEmail;

    public OrderTrackingModel() {
    }

    public OrderTrackingModel(Long orderId, Long customerId, String customerEmail,
            OrderStatusEnum previousStatus, OrderStatusEnum currentStatus,
            LocalDateTime date, Long employeeId, String employeeEmail) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.previousStatus = previousStatus;
        this.currentStatus = currentStatus;
        this.date = date;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public OrderStatusEnum getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(OrderStatusEnum previousStatus) {
        this.previousStatus = previousStatus;
    }

    public OrderStatusEnum getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(OrderStatusEnum currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
