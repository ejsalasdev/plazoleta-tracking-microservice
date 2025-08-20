package com.plazoleta.trackingmicroservice.infrastructure.exceptionhandler;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response for exception handling")
public record ExceptionResponse(
        @Schema(description = "Error message", example = "Order tracking history not found.")
        String message,
        @Schema(description = "Timestamp when the error occurred", example = "2024-03-15T10:30:00")
        LocalDateTime timestamp
) {}
