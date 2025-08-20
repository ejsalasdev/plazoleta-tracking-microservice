package com.plazoleta.trackingmicroservice.application.dto.response;

public record OrderEfficiencyResponse(
        Long orderId,
        String startTime,
        String endTime,
        String duration) {

}
