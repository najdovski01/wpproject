package com.example.wpproject.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/home"})
public class HomeController {

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("bodyContent", "access_denied");
        return "master-template";
    }

    //api map
    @GetMapping("/mymap")
    public String getMap(){
        return "mymap";
    }

    @GetMapping("/calendar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String calendar() {
        return "calendar";
    }
}
