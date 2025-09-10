package com.example.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Serves the single-page UI */
@Controller
public class WebController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
