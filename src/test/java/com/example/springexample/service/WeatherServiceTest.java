package com.example.springexample.service;


import com.example.springexample.config.WeatherConfiguration;
import com.example.springexample.dto.GeoCityDto;
import com.example.springexample.dto.WeathersDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;


import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
public class WeatherServiceTest {

    private static MockWebServer mockWebServer=new MockWebServer();
    private WeatherService service;


    @BeforeEach
    public void setUp() throws IOException {

//        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient builder = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString()).build();

        WeatherConfiguration config = new WeatherConfiguration();
        config.setBaseUrl(mockWebServer.url("/").toString());
        config.setKey("test-key");

        service=new WeatherService(builder, new ObjectMapper(), config);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }


    @Test
    @DisplayName("get current geodata by City test")
    public void getCurrentGeodataByCityTest() {

        String mockResponse = """
                [
                    {
                        "name": "London",
                        "lat": 51.5074,
                        "lon": -0.1276,
                        "country": "GB",
                        "state": "England"
                    },
                    {
                        "name": "London",
                        "lat": 42.9834,
                        "lon": -81.233,
                        "country": "CA",
                        "state": "Ontario"
                    }
                ]
                """;
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .addHeader("Content-Type", "application/json"));

        List<GeoCityDto> result = service.getGeoCity("London");

        assertNotNull(result);
        assertEquals(2, result.size());

        GeoCityDto firstCity = result.get(0);
        assertEquals("London", firstCity.getCity());
        assertEquals(51.5074, firstCity.getLatitude(),0.0001);
        assertEquals(-0.1276, firstCity.getLongitude(),0.0001);
        assertEquals("GB", firstCity.getCountry());
    }

    @Test
    @DisplayName("get not found city test")
    public void getNotFoundCityTest() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("[]")
                .addHeader("Content-Type", "application/json"));

        assertThrows(IllegalStateException.class, () -> {
            service.getGeoCity("UnknownCity");
        });
    }

    @Test
    @DisplayName("get error 404 test")
    public void getError404Test() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody("Not Found"));


        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            service.getGeoCity("InvalidCity");
        });

        assertEquals("Город не найден в ответе OpenWeather", exception.getMessage());
    }

    @Test
    @DisplayName("Успешное получение текущей погоды")
    public void getCurrentWeather_Success() throws Exception {

        String weatherMockResponse = """
                {
                    "name": "London",
                    "main": {
                        "temp": 17.33,
                        "feels_like": 16.9,
                        "humidity": 65
                    },
                    "weather": [
                        {
                            "main": "Clear",
                            "description": "clear sky"
                        }
                    ]
                }
                """;
        mockWebServer.enqueue(new MockResponse()
                .setBody(weatherMockResponse)
                .addHeader("Content-Type", "application/json"));

        Optional<WeathersDto> result = service.getWeather(51.5074,-0.1278);

        assertNotNull(result);
        assertEquals("London", result.get().getName());
        assertEquals(17.33, result.get().getTemperature());
        assertEquals("Clear", result.get().getMainConditionWeather());
        assertEquals("clear sky", result.get().getDescription());
    }

    @Test
    @DisplayName("Ошибка сервера (500) при запросе погоды")
    public void getCurrentWeather_ServerError() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

         assertThrows(IllegalStateException.class, () -> {
            service.getWeather(51.5074, -0.1278);
        });

    }




}
