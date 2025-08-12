package com.example.springexample.controller;


import com.example.springexample.dto.WeathersDto;
import com.example.springexample.service.LocationService;
import com.example.springexample.service.SessionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ControllerMainPage {

    private final SessionsService sessionsService;
    private final LocationService locationService;

    @RestController
    public class TestController {
        @GetMapping("/ping")
        public String ping() {
            return "pong";
        }
    }

    @GetMapping("/index")
    public String signUp(Model model, @CookieValue(value = "id", required = false) String id) {
        if (id != null) {
            if (sessionsService.expireSession(id)) {
                model.addAttribute("authorization", "signIn");
            } else {
                model.addAttribute("authorization", "signOut");
                List<WeathersDto> listWeatherDto = locationService.getWeather(id);
                if (listWeatherDto.size() > 5) {
                    return "error";
                }
                else {
                    model.addAttribute("weathers", listWeatherDto);
                }
            }
        } else {
            model.addAttribute("authorization", "signUp");
        }

        return "index";
    }


    @GetMapping("/sign-up")
    public String redirectSignUp() {
        return "redirect:/login";
    }

    @GetMapping("/sign-in")
    public String redirectSignIn() {
        return "redirect:/login-sign-in";
    }

    @GetMapping("/Search")
    public String redirectSearch(@ModelAttribute ("name") String userInput, RedirectAttributes redirectAttributes,  @RequestParam("authorization") String authorization ) {

        if (authorization.equals("signIn") || authorization.equals("signUp")) {
            return "error";
        }else {
            redirectAttributes.addAttribute("name", userInput);
            redirectAttributes.addAttribute("value", userInput);
            return "redirect:/search-results";
        }

    }

    @PostMapping("/delete")
    public String deleteWeather(@ModelAttribute WeathersDto weathersDto, @CookieValue(value = "id", required = false) String id) {
        if (id != null) {
            locationService.deleteLocation(weathersDto.getName(), id);
        }
        return "redirect:/index";
    }


}
