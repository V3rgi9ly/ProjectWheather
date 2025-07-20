package com.example.springexample.controller;


import com.example.springexample.dto.WheatherDto;
import com.example.springexample.model.Weather;
import com.example.springexample.service.WheatherService;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ControllerWheather {
    private final WheatherService weatherService;


//    @GetMapping()
//    public ResponseEntity getWeather(@RequestParam double lat, @RequestParam double lon) {
//        WeatherInformation weatherInfo = weather.getWeatherData(lat, lon);
//        return ResponseEntity.ok().body(weatherInfo);
//    }

    @GetMapping("/search-results")
    public String searchResults(@ModelAttribute("name") String userInput) {
        WheatherDto weather=weatherService.getWeather(userInput);
        System.out.println(weather.toString());
        System.out.println(userInput);
        return "search-results";
    }
}
