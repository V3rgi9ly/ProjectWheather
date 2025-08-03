package com.example.springexample.service;

import com.example.springexample.dto.GeoCityDto;
import com.example.springexample.dto.WeathersDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {
    //    private final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final String WEATHER_API_KEY = "7a5864eb5cac7c74e9bc2a2e3a5023bf";
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public WeatherService(WebClient.Builder clientBuilder, ObjectMapper objectMapper) {
        this.webClient = clientBuilder.build();
        this.objectMapper = objectMapper;

    }

    public List<GeoCityDto> getGeoCity(String city) {
        try {

            String jsonpObject = WebClient.create("https://api.openweathermap.org").get().
                    uri(uriBuilder -> uriBuilder
                            .path("/geo/1.0/direct")
                            .queryParam("q", city)
                            .queryParam("limit", 5)
                            .queryParam("appid", WEATHER_API_KEY)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class).block();


            System.out.println(jsonpObject);
            JsonNode locations = objectMapper.readTree(jsonpObject);
            System.out.println(locations);

            List<Optional<GeoCityDto>> geoCityDtoList = new ArrayList<>();
            for (JsonNode node : locations) {
                GeoCityDto geoCityDto = new GeoCityDto();
                geoCityDto.setCity(node.path("name").asText());
                geoCityDto.setCountry(node.path("country").asText());
                geoCityDto.setLatitude(node.path("lat").asDouble());
                geoCityDto.setLongitude(node.path("lon").asDouble());

                geoCityDtoList.add(Optional.of(geoCityDto));
            }

            List<GeoCityDto> unpackedWeatherDtos = geoCityDtoList.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            System.out.println(unpackedWeatherDtos);
            return unpackedWeatherDtos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<WeathersDto> getWeather(double lat, double lon) {

        if (lat == 0 && lon == 0) {
            throw new IllegalStateException("Invalid coordinates");
        }

        try {
            String jsonpObject = WebClient.create("https://api.openweathermap.org").get().
                    uri(uriBuilder -> uriBuilder
                            .path("/data/2.5/weather")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("units", "metric")
                            .queryParam("appid", WEATHER_API_KEY)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class).block();


            JsonNode jsonNode = objectMapper.readTree(jsonpObject);
            WeathersDto dto = new WeathersDto();
            dto.setName(jsonNode.path("name").asText());
            dto.setDescription(jsonNode.path("weather").get(0).path("description").asText());
            dto.setIconWeather(jsonNode.path("weather").get(0).path("icon").asText());
            dto.setLatitude(jsonNode.path("coord").path("lat").asDouble());
            dto.setLongitude(jsonNode.path("coord").path("lon").asDouble());
            dto.setMainConditionWeather(jsonNode.path("weather").get(0).path("main").asText());
            dto.setHumidity(jsonNode.path("main").path("humidity").asInt());
            dto.setTemperature(jsonNode.path("main").path("temp").asDouble());
            dto.setFeels_like_temperature(jsonNode.path("main").path("feels_like").asInt());

            return Optional.of(dto);
        } catch (WebClientResponseException e) {
            System.err.println("OpenWeather API returned error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw new IllegalStateException("Ошибка API: " + e.getMessage(), e);
        } catch (WebClientRequestException e) {
            System.err.println("Request to OpenWeather failed: " + e.getMessage());
            throw new IllegalStateException("Ошибка подключения к OpenWeather API", e);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
