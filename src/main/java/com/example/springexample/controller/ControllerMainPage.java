package com.example.springexample.controller;


import com.example.springexample.service.SessionsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ControllerMainPage {

    private final SessionsService sessionsService;

    @GetMapping("/index")
    public String signUp( Model model, HttpServletRequest request) {
        String id="";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    id = cookie.getValue();
                }
            }

            if (sessionsService.expireSession(id)){
                model.addAttribute("authorization", "signIn");
            }
            else {
                model.addAttribute("authorization", "signOut");
            }
        }else {
            model.addAttribute("authorization", "signUp");
        }


        return "index";
    }


    @GetMapping("/sign-up")
    public String redirectSignUp(RedirectAttributes model) {
        return "redirect:/login";
    }

    @GetMapping("/sign-in")
    public String redirectSignIn(RedirectAttributes model) {
        return "redirect:/login-sign-in";
    }

    @GetMapping("/sign-in")
    public String redirectSignOut(RedirectAttributes model) {
        return "redirect:/index";
    }
}
