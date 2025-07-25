package com.example.springexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {


    @Bean
    public WebClient.Builder beanRestTemplate() {
        return  WebClient.builder();
    }

}
