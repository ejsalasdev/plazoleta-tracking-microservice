package com.plazoleta.trackingmicroservice.infrastructure.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.plazoleta.trackingmicroservice.domain.enums.OrderStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order_tracking")
public class OrderTrackingEntity {

    @Id
    private String id;

    @Field("order_id")
    private Long orderId;

    @Field("restaurant_id")
    private Long restaurantId;

    @Field("customer_id")
    private Long customerId;

    @Field("customer_email")
    private String customerEmail;

    @Field("previous_status")
    private OrderStatusEnum previousStatus;

    @Field("current_status")
    private OrderStatusEnum currentStatus;

    @Field("date")
    private LocalDateTime date;

    @Field("employee_id")
    private Long employeeId;

    @Field("employee_email")
    private String employeeEmail;

}