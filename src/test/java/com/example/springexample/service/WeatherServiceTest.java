package com.example.springexample.service;


import com.example.springexample.dto.GeoCityDto;
import com.example.springexample.dto.WeathersDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {


    @Mock
    private WebClient webClient;

    @Autowired
    private WeatherService service;

    private final String apiKey = "7a5864eb5cac7c74e9bc2a2e3a5023bf";
    private final String urlGeoCity = "https://api.openweathermap.org/geo/1.0/direct";
    private final String urlWeather = "https://api.openweathermap.org/data/2.5/weather";


    @Test
    @DisplayName("get current weather test")
    public void getCurrentWeatherTest() {

        WeathersDto weathersDto = new WeathersDto();
        weathersDto.setName("London");
        weathersDto.setHumidity(65);
        weathersDto.setMainConditionWeather("Clouds");
        weathersDto.setTemperature(294.71);
        weathersDto.setDescription("overcast clouds");
        weathersDto.setFeels_like_temperature(294.62);

       List<GeoCityDto> result = service.getGeoCity("London");

//        assertNotNull(result);
//        assertEquals("London", result.get().getName());
//        assertEquals("Clouds", result.get().getMainConditionWeather());

    }

    @Test
    @DisplayName("get current  weather when city not found")
    public void getCurrentWeatherWhenCityNotFoundTest() {
        String city = "sdfsdfdsdsf";

        assertThrows(IllegalStateException.class,()->{
            service.getGeoCity(city);
        });
    }
}
