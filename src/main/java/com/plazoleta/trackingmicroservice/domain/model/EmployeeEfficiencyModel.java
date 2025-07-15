package com.plazoleta.trackingmicroservice.domain.model;

import java.time.Duration;

public class EmployeeEfficiencyModel {
    
    private final Long employeeId;
    private final Integer completedOrders;
    private final Duration averageDuration;

    public EmployeeEfficiencyModel(Long employeeId, Integer completedOrders, Duration averageDuration) {
        this.employeeId = employeeId;
        this.completedOrders = completedOrders;
        this.averageDuration = averageDuration;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Integer getCompletedOrders() {
        return completedOrders;
    }

    public Duration getAverageDuration() {
        return averageDuration;
    }
}
