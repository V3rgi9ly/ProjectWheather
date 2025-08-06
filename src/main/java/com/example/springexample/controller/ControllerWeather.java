package com.example.springexample.controller;


import com.example.springexample.dto.GeoCityDto;
import com.example.springexample.dto.WeathersDto;
import com.example.springexample.service.LocationService;
import com.example.springexample.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ControllerWeather {
    private final WeatherService weatherService;
    private final LocationService locationService;


    @GetMapping("/search-results")
    public String searchResults(@ModelAttribute("name") String userInput, Model model) {
        List<GeoCityDto> geoCity = weatherService.getGeoCity(userInput);
        model.addAttribute("weatherDto", geoCity);
        return "search-results";
    }


    @PostMapping("/search-results")
    public String addWeather(@ModelAttribute WeathersDto weathersDto, @CookieValue(value = "id", required = false) String id) {
        Optional<WeathersDto> weather = weatherService.getWeather(weathersDto.getLatitude(), weathersDto.getLongitude());
        if (weather.isPresent()) {
            if (id != null) {
                boolean duplicate = locationService.сheckingForDuplicates(Optional.of(weather.get()), id);
                List<WeathersDto> listWeatherDto = locationService.getWeather(id);
                if (listWeatherDto.size() >= 5 || !duplicate) {
                    return "error";
                }else {
                    locationService.saveLocation(weather.get(), id);
                }
            }
            return "redirect:/index";
        } else {
            return "error";
        }
    }
}
