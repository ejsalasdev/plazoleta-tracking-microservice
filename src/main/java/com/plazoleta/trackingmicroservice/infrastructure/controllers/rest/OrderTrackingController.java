package com.plazoleta.trackingmicroservice.infrastructure.controllers.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plazoleta.trackingmicroservice.application.dto.request.OrderTrackingRequest;
import com.plazoleta.trackingmicroservice.application.dto.response.OrderTrackingResponse;
import com.plazoleta.trackingmicroservice.application.handler.OrderTrackingHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tracking")
@RequiredArgsConstructor
@Tag(name = "Order Tracking", description = "Order tracking and traceability operations")
public class OrderTrackingController {

    private final OrderTrackingHandler orderTrackingHandler;

    @PostMapping("/internal/track")
    @Operation(summary = "Register order status change", description = "Internal endpoint used by foodcourt-microservice to register order status changes")
    @ApiResponse(responseCode = "201", description = "Order tracking record created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderTrackingResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    public ResponseEntity<OrderTrackingResponse> trackOrderStatusChange(
            @Parameter(description = "Order tracking information", required = true) @Valid @RequestBody OrderTrackingRequest request) {

        OrderTrackingResponse response = orderTrackingHandler.createOrderTracking(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Get order tracking history", description = "Retrieve the complete tracking history for a specific order. Only the customer who owns the order can access this information.")
    @ApiResponse(responseCode = "200", description = "Order tracking history retrieved successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderTrackingResponse.class))))
    @ApiResponse(responseCode = "404", description = "Order tracking not found", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "403", description = "Access denied - order does not belong to authenticated user", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content(mediaType = "application/json"))
    public ResponseEntity<List<OrderTrackingResponse>> getOrderTrackingHistory(
            @Parameter(description = "Order ID to get tracking history for", example = "12345") @PathVariable Long orderId) {

        List<OrderTrackingResponse> response = orderTrackingHandler.getOrderTrackingHistory(orderId);

        return ResponseEntity.ok(response);
    }
}
