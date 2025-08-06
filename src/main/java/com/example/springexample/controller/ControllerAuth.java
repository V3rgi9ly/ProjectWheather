package com.example.springexample.controller;

import com.example.springexample.dto.UsersDto;
import com.example.springexample.service.SessionsService;
import com.example.springexample.service.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String saveData(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");

        if (password.equals(repeatPassword)) {
            UsersDto usersDto = usersService.addUsers(username, password);
            String uuid = sessionsService.createSessions(usersDto);
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
    public String authorizationInput(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsersDto usersDto=usersService.getUser(username);
        if (usersService.dataVerification(username, password)) {
           String uuid= sessionsService.createSessions(usersDto);
           response.addCookie(new Cookie("id", uuid));
        }else {
            return "sign-in-with-errors";
        }

        return "redirect:/index";
    }


    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request) {
        Cookie[] cookie=request.getCookies();
        String id="";
        if(cookie != null){
            for(Cookie c:cookie){
                if(c.getName().equals("id")){
                    id=c.getValue();
                }
            }
        }
        sessionsService.deleteSession(id);
        return "redirect:/index";
    }




}
