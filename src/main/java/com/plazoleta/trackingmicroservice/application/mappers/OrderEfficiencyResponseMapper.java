package com.plazoleta.trackingmicroservice.application.mappers;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.plazoleta.trackingmicroservice.application.dto.response.OrderEfficiencyResponse;
import com.plazoleta.trackingmicroservice.domain.model.OrderEfficiencyModel;

@Component
public class OrderEfficiencyResponseMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<OrderEfficiencyResponse> toEfficiencyResponseList(List<OrderEfficiencyModel> models) {
        return models.stream()
                .map(this::modelToResponse)
                .toList();
    }

    public OrderEfficiencyResponse modelToResponse(OrderEfficiencyModel model) {
        return new OrderEfficiencyResponse(
                model.getOrderId(),
                model.getStartTime().format(DATE_TIME_FORMATTER),
                model.getEndTime().format(DATE_TIME_FORMATTER),
                formatDuration(model.getDuration())
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
