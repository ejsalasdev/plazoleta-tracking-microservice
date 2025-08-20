package com.plazoleta.trackingmicroservice.common.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Tracking Microservice API",
                version = "1.0",
                description = """
                        # 🔍 Order Tracking Microservice API
                        
                        Microservice responsible for tracking order status changes and providing traceability functionality for the Plazoleta de Comidas platform.
                        
                        ## 📋 Key Features:
                        - ✅ Order status change tracking
                        - ✅ Traceability history for customers
                        - ✅ MongoDB persistence
                        - ✅ Hexagonal architecture + DDD
                        - ✅ JWT authentication
                        - ✅ Integration with foodcourt microservice
                        
                        """,
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development",
                        url = "http://localhost:8094"
                )
        },
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
