package com.example.springexample.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControllerMainPage {


    @GetMapping("/")
    public String signUp(@CookieValue(value = "id", defaultValue = "default_value")String id, Model model) {
        if (id.equals("default_value")) {
            model.addAttribute("signBool", true);
            model.addAttribute("sign", "Sign Up");
        }else {
            model.addAttribute("signBool", false);
            model.addAttribute("sign", "Sign In");
        }
        return "index";
    }


    @GetMapping("/sign")
    public String redirectSign(RedirectAttributes model) {
        return "redirect:/login";
    }
}
