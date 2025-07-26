package com.example.springexample.controller;


import com.example.springexample.dto.CoordinateDto;
import com.example.springexample.dto.GeoCityDto;
import com.example.springexample.dto.WeathersDto;
import com.example.springexample.service.LocationService;
import com.example.springexample.service.WheatherService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ControllerWheather {
    private final WheatherService weatherService;
    private final LocationService locationService;


    @GetMapping("/search-results")
    public String searchResults(@ModelAttribute("name") String userInput, Model model) {
        List<GeoCityDto> geoCity = weatherService.getGeoCity(userInput);
//        GeoCityDto weatherDto = geoCity.get();

        model.addAttribute("weatherDto", geoCity);
//        model.addAttribute("city", weatherDto.getCity());
//        model.addAttribute("Latitude", weatherDto.getCoordinate().getLatitude());
//        model.addAttribute("Longitude", weatherDto.getCoordinate().getLongitude());
//        model.addAttribute("Country", weatherDto.getCountry().getCountry());
        return "search-results";
    }


    @PostMapping("/search-results")
    public String addWeather(@ModelAttribute CoordinateDto coordinateDto, HttpServletRequest request) {
        Optional<WeathersDto> weather = weatherService.getWeather(coordinateDto.getLatitude(), coordinateDto.getLongitude());
        String id = "";
        if (weather.isPresent()) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("id")) {
                        id = cookie.getValue();
                    }
                }
            }
            List<WeathersDto> listWeatherDto = locationService.getWeather(id);
            if (listWeatherDto.size() >= 5) {
                return "error";
            }else {
                locationService.saveLocation(weather.get(), id);
            }
            return "redirect:/index";
        } else {
            return "error";
        }
    }
}
