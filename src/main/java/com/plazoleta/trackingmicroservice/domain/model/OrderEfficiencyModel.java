package com.plazoleta.trackingmicroservice.domain.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class OrderEfficiencyModel {
    
    private Long orderId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration duration;

    public OrderEfficiencyModel() {
    }

    public OrderEfficiencyModel(Long orderId, LocalDateTime startTime, LocalDateTime endTime, Duration duration) {
        this.orderId = orderId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
