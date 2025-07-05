package com.example.springexample.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControllerMainPage {


    @GetMapping("/")
    public String signUp() {
        return "index";
    }


    @GetMapping("/sign")
    public String redirectSign(RedirectAttributes model) {
        return "redirect:/login";
    }
}
