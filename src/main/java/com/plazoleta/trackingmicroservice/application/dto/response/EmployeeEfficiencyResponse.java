package com.plazoleta.trackingmicroservice.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Employee efficiency information with average order completion time")
public record EmployeeEfficiencyResponse(
        
        @Schema(description = "Employee ID", example = "101")
        Long employeeId,
        
        @Schema(description = "Number of completed orders by this employee", example = "15")
        Integer completedOrders,
        
        @Schema(description = "Average time to complete orders (formatted)", example = "25 minutes, 30 seconds")
        String averageTime
) {
}
