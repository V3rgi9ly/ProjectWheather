package com.example.springexample.controller;

import com.example.springexample.dto.UsersDto;
import com.example.springexample.service.CreateSessionsService;
import com.example.springexample.service.RegistrationUsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ControllerLogin {

    private final RegistrationUsersService registrationUsersService;
    private final CreateSessionsService createSessionsService;

    @GetMapping("/login")
    public String login(Model model) {
        return "sign-up";
    }

    @PostMapping("/login")
    public String saveData(@RequestParam("Username") String username, @RequestParam("Password") String password,
                           @RequestParam("RepeatPassword") String repeatPasswor, Model model, HttpServletResponse response) {

            if (password.equals(repeatPasswor)) {
               UsersDto usersDto= registrationUsersService.addUsers(username,password);
                String uuid= createSessionsService.createSessions(usersDto);
                Cookie cookie = new Cookie("id",uuid);
            }else {
                return "sign-up-with-errors";
            }

            return "index";
    }
}
