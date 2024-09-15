package com.finki.journalingapplication.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@AllArgsConstructor
public class HomePageController {
    @GetMapping("/homepage")
    private String mainPage() {
        return "index";
    }

}
