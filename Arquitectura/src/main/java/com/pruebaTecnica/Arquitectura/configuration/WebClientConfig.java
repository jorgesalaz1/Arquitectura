package com.pruebatecnica.arquitectura.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {
    @Value("${api.base-url}")
    private String apiUrl;
    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder
                .baseUrl(apiUrl)
                .build();
    }
}
