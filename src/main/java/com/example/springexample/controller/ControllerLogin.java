package com.example.springexample.controller;

import com.example.springexample.dto.SessionsDto;
import com.example.springexample.dto.UsersDto;
import com.example.springexample.dto.VerificationUserDto;
import com.example.springexample.model.Users;
import com.example.springexample.service.CreateSessionsService;
import com.example.springexample.service.RegistrationUsersService;
import com.example.springexample.service.SessionsService;
import com.example.springexample.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UsersService usersService;
    private final ObjectMapper objectMapper;
    private final SessionsService sessionsService;

    @GetMapping("/login")
    public String login(Model model) {
        return "sign-up";
    }

    @PostMapping("/login")
    public String saveData(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");

        if (password.equals(repeatPassword)) {
            UsersDto usersDto = registrationUsersService.addUsers(username, password);
            String uuid = createSessionsService.createSessions(usersDto);
            Cookie cookie = new Cookie("id", uuid);
            response.addCookie(cookie);
        }
        else {
            return "sign-up-with-errors";
        }

        return "redirect:/index";
    }



    @GetMapping("/login-sign-in")
    public String authorizationPage(){
        return "sign-in";
    }

    @PostMapping("/login-sign-in")
    public String authorizationInput(HttpServletRequest request, Model model, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Cookie[] cookie=request.getCookies();
        String id="";

        if(cookie != null){
            for(Cookie c:cookie){
                if(c.getName().equals("id")){
                    id=c.getValue();
                }
            }
        }

        Users user=usersService.sessionAvailabilityCheck(id);
        UsersDto usersDto=objectMapper.convertValue(user, UsersDto.class);
        if (usersService.dataVerification(user, username, password)) {
           String uuid= createSessionsService.createSessions(usersDto);
           response.addCookie(new Cookie("id", uuid));
        }else {
            return "sign-in-with-errors";
        }

        return "redirect:/index";
    }



    @PostMapping ("/index")
    public String SignOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookie=request.getCookies();
        String id="";
        if(cookie != null){
            for(Cookie c:cookie){
                if(c.getName().equals("id")){
                    id=c.getValue();
                }
            }
        }
        SessionsDto sessionsDto=sessionsService.getSession(id);
        String uuid=sessionsService.endOfSession(sessionsDto);
        Cookie cookie1=new Cookie("id",uuid);
        response.addCookie(cookie1);
        return "redirect:/index";
    }
}
