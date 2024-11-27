package com.megazone.ERPSystem_phase3_FinanceHR.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final SecretManagerConfig secretManagerConfig;

    @Bean
    public RestClient notificationServiceClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8082/api/notifications/")
                .build();
    }

    @Bean
    public RestClient integratedServiceClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8082/api/integrated/")
                .build();
    }
}
