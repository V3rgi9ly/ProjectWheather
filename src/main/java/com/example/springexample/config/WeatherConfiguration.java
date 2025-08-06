package com.example.springexample.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Setter
@Configuration
public class WeatherConfiguration {

    @Value("${weather.api.baseUrl}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String key;


    @Bean
    public WebClient beanRestTemplate() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
