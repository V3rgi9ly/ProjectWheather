package com.example.springexample.controller;

import com.example.springexample.dto.UsersDto;
import com.example.springexample.service.SessionsService;
import com.example.springexample.service.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ControllerAuth {

    private final UsersService usersService;
    private final SessionsService sessionsService;

    @GetMapping("/login")
    public String login() {
        return "sign-up";
    }

    @PostMapping("/login")
    public String saveData(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("repeatPassword") String repeatPassword, HttpServletResponse response) {

        if (password.equals(repeatPassword)) {
            UsersDto usersDto = usersService.addUsers(username, password);
            String uuid = sessionsService.createSessions(usersDto);
            Cookie cookie = new Cookie("id", uuid);
            response.addCookie(cookie);
        } else {
            return "sign-up-with-errors";
        }

        return "redirect:index";
    }


    @GetMapping("/login-sign-in")
    public String authorizationPage() {
        return "sign-in";
    }

    @PostMapping("/login-sign-in")
    public String authorizationInput(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {
        UsersDto usersDto = usersService.getUser(username);
        if (usersService.dataVerification(username, password)) {
            String uuid = sessionsService.createSessions(usersDto);
            response.addCookie(new Cookie("id", uuid));
        } else {
            return "sign-in-with-errors";
        }

        return "redirect:/index";
    }

    @PostMapping("/login-sign-up")
    public String registrationUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("repeatPassword") String repeatPassword, HttpServletResponse response) {

        if (password.equals(repeatPassword)) {
            UsersDto usersDto = usersService.addUsers(username, password);
            String uuid = sessionsService.createSessions(usersDto);
            Cookie cookie = new Cookie("id", uuid);
            response.addCookie(cookie);
        } else {
            return "sign-up-with-errors";
        }

        return "redirect:/index";
    }


    @GetMapping("/logOut")
    public String logOut(@CookieValue(value = "id", required = false ) String id) {

        if (id != null) {
            sessionsService.deleteSession(id);
        }

        return "redirect:/index";
    }


}
