package com.example.springexample.service;

import com.example.springexample.dto.WheatherDto;
import com.example.springexample.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class WheatherService {
//    private final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final String WEATHER_API_KEY = "643d0e650c65dc997218dc1d503593e1";
    private final WebClient webClient;

//    @Autowired
    public WheatherService(WebClient.Builder clientBuilder) {
        this.webClient = clientBuilder.baseUrl("http://api.openweathermap.org/data/2.5/weather").build();
    }

    public WheatherDto getWeather(String city) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("appid",WEATHER_API_KEY)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Weather.class)
                .map(weather ->{
                    WheatherDto weather1=new WheatherDto();
                    weather1.setCity(weather.getCity());
                    weather1.setTemp(weather.getTemp());
                    weather1.setLongitude(weather.getLongitude());
                    weather1.setCountry(weather.getCountry());
                    return weather1;
                })
                .block();
    }

//    public WeatherInformation getWeatherData(double lat, double lon){
//        String url=String.format(WEATHER_API_URL,lat,lon,WEATHER_API_KEY);
//        return restTemplate.getForObject(url, WeatherInformation.class);
//    }


}
