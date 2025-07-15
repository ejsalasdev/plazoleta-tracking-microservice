package com.plazoleta.trackingmicroservice.common.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.plazoleta.trackingmicroservice.application.client.handler.FoodcourtHandlerClient;
import com.plazoleta.trackingmicroservice.domain.ports.in.OrderTrackingServicePort;
import com.plazoleta.trackingmicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.trackingmicroservice.domain.ports.out.OrderTrackingPersistencePort;
import com.plazoleta.trackingmicroservice.domain.ports.out.external.RestaurantServicePort;
import com.plazoleta.trackingmicroservice.domain.usecases.OrderTrackingUseCase;
import com.plazoleta.trackingmicroservice.infrastructure.adapters.external.RestaurantServiceAdapter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Bean
    public RestaurantServicePort restaurantServicePort(FoodcourtHandlerClient foodcourtHandlerClient) {
        return new RestaurantServiceAdapter(foodcourtHandlerClient);
    }

    @Bean
    public OrderTrackingServicePort orderTrackingPort(OrderTrackingPersistencePort orderTrackingPersistencePort,
            AuthenticatedUserPort authenticatedUserPort, RestaurantServicePort restaurantServicePort) {
        return new OrderTrackingUseCase(orderTrackingPersistencePort, authenticatedUserPort, restaurantServicePort);
    }
}
