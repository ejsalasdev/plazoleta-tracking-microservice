package com.plazoleta.trackingmicroservice.application.dto.response;

import java.time.LocalDateTime;

import com.plazoleta.trackingmicroservice.domain.enums.OrderStatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing order tracking information")
public record OrderTrackingResponse(

        @Schema(description = "Unique tracking record ID", example = "60f3b4b3e4b0c4a7b8c9d1e2")
        String id,

        @Schema(description = "Order ID", example = "12345")
        Long orderId,

        @Schema(description = "Customer ID", example = "67890")
        Long customerId,

        @Schema(description = "Customer email", example = "customer@plazoleta.com")
        String customerEmail,

        @Schema(description = "Previous order status", example = "PENDING")
        OrderStatusEnum previousStatus,

        @Schema(description = "Current order status", example = "IN_PREPARATION")
        OrderStatusEnum currentStatus,

        @Schema(description = "Date and time of the status change", example = "2024-03-15T10:30:00")
        LocalDateTime date,

        @Schema(description = "Employee ID who made the change", example = "11111")
        Long employeeId,

        @Schema(description = "Employee email who made the change", example = "employee@plazoleta.com")
        String employeeEmail

) {
}