package com.plazoleta.trackingmicroservice.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.plazoleta.trackingmicroservice.infrastructure.security.jwt.JwtTokenValidator;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenValidator jwtTokenValidator;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // Public endpoints
                    http.requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs*/**").permitAll();
                    http.requestMatchers("/actuator/health").permitAll();
                    
                    // Order tracking endpoints - require authentication
                    http.requestMatchers(HttpMethod.GET, "/tracking/**").authenticated();
                    http.requestMatchers(HttpMethod.POST, "/tracking/**").authenticated();
                    
                    // Deny all other requests
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(jwtTokenValidator, BasicAuthenticationFilter.class)
                .build();
    }
}
