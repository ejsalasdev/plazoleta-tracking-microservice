package com.plazoleta.trackingmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TrackingMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackingMicroserviceApplication.class, args);
	}

}
