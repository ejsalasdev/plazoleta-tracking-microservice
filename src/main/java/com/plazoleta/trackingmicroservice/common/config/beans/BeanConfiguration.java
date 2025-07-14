package com.plazoleta.trackingmicroservice.common.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.plazoleta.trackingmicroservice.domain.ports.in.OrderTrackingServicePort;
import com.plazoleta.trackingmicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.trackingmicroservice.domain.ports.out.OrderTrackingPersistencePort;
import com.plazoleta.trackingmicroservice.domain.usecases.OrderTrackingUseCase;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    
    @Bean
    public OrderTrackingServicePort orderTrackingPort(OrderTrackingPersistencePort orderTrackingPersistencePort,
                                                      AuthenticatedUserPort authenticatedUserPort) {
        return new OrderTrackingUseCase(orderTrackingPersistencePort, authenticatedUserPort);
    }
}
