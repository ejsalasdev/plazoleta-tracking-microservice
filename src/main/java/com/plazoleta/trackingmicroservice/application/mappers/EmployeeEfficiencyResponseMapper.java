package com.plazoleta.trackingmicroservice.application.mappers;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Component;

import com.plazoleta.trackingmicroservice.application.dto.response.EmployeeEfficiencyResponse;
import com.plazoleta.trackingmicroservice.domain.model.EmployeeEfficiencyModel;

@Component
public class EmployeeEfficiencyResponseMapper {

    public List<EmployeeEfficiencyResponse> toEmployeeEfficiencyResponseList(List<EmployeeEfficiencyModel> models) {
        return models.stream()
                .map(this::modelToResponse)
                .toList();
    }

    public EmployeeEfficiencyResponse modelToResponse(EmployeeEfficiencyModel model) {
        return new EmployeeEfficiencyResponse(
                model.getEmployeeId(),
                model.getCompletedOrders(),
                formatDuration(model.getAverageDuration())
        );
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        
        if (hours > 0) {
            return String.format("%d hours, %d minutes, %d seconds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%d minutes, %d seconds", minutes, seconds);
        } else {
            return String.format("%d seconds", seconds);
        }
    }
}
