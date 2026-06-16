package com.pruebatecnica.arquitectura.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {
     public static final  String URL = "http://localhost:8080/api/v1/";
    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder
                .baseUrl(URL)
                .build();
    }
}
