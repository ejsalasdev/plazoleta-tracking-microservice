package com.plazoleta.trackingmicroservice.application.dto.response;

import java.time.LocalDateTime;

import com.plazoleta.trackingmicroservice.domain.enums.OrderStatusEnum;

public record OrderTrackingResponse(

        String id,

        Long orderId,

        Long customerId,

        String customerEmail,

        OrderStatusEnum previousStatus,

        OrderStatusEnum currentStatus,

        LocalDateTime date,

        Long employeeId,

        String employeeEmail

) {
}