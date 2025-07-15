package com.plazoleta.trackingmicroservice.application.client.handler;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.plazoleta.trackingmicroservice.application.client.dto.RestaurantResponse;

@FeignClient(name = "foodcourt-microservice", url = "${foodcourt-microservice.url}")
public interface FoodcourtHandlerClient {

    @GetMapping("/api/v1/restaurant/owner/{ownerId}")
    RestaurantResponse getRestaurantByOwnerId(@PathVariable Long ownerId);
}
