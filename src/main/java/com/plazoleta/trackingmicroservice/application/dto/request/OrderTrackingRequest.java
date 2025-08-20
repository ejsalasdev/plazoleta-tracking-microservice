package com.plazoleta.trackingmicroservice.application.dto.request;

import com.plazoleta.trackingmicroservice.domain.enums.OrderStatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Request object for tracking order status changes")
public record OrderTrackingRequest(
        
        @Schema(description = "Order ID", example = "12345")
        Long orderId,
        
        @Schema(description = "Customer ID", example = "67890")
        Long customerId,
        
        @Schema(description = "Customer email", example = "customer@plazoleta.com")
        String customerEmail,
        
        @Schema(description = "Previous order status", example = "PENDING")
        OrderStatusEnum previousStatus,
        
        @Schema(description = "New order status", example = "IN_PREPARATION")
        OrderStatusEnum currentStatus,
        
        @Schema(description = "Employee ID who made the change", example = "11111")
        Long employeeId,
        
        @Schema(description = "Employee email who made the change", example = "employee@plazoleta.com")
        String employeeEmail
        
) {
}
