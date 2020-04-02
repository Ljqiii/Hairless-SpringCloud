package com.ljqiii.hairlessauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

    @GetMapping("/")
    public String homepage() {
        return "home";
    }
}
