package com.example.springexample.service;

import com.example.springexample.dto.GeoCityDto;
import com.example.springexample.dto.WeathersDto;
import com.example.springexample.model.GeoCity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class WheatherService {
    //    private final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final String WEATHER_API_KEY = "643d0e650c65dc997218dc1d503593e1";
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public WheatherService(WebClient.Builder clientBuilder, ObjectMapper objectMapper) {
        this.webClient = clientBuilder.baseUrl("http://api.openweathermap.org/data/2.5/weather").build();
        this.objectMapper = objectMapper;

    }

    public Optional<GeoCityDto> getGeoCity(String city) {
        try {
            String jsonpObject = webClient.get().
                    uri(uriBuilder -> uriBuilder
                            .queryParam("q", city)
                            .queryParam("appid", WEATHER_API_KEY)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class).block();

            GeoCityDto dto = objectMapper.readValue(jsonpObject, GeoCityDto.class);
            return Optional.of(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<WeathersDto> getWeather(double latitude, double longitude) {


        try {
            String jsonpObject = webClient.get().
                    uri(uriBuilder -> uriBuilder
                            .queryParam("lat", latitude)
                            .queryParam("lon", longitude)
                            .queryParam("appid", WEATHER_API_KEY)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class).block();


            GeoCity geoCity = objectMapper.readValue(jsonpObject, GeoCity.class);
            System.out.println(geoCity);
            System.out.println(jsonpObject);
            WeathersDto dto = objectMapper.readValue(jsonpObject, WeathersDto.class);
            System.out.println(dto.toString());
            return Optional.of(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
