package com.ljqiii.hairlessauth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePage {

    @Value("${hairless.login}")
    String hairlessLogin;

    @GetMapping("/")
    public String homepage() {
        return "redirect://" + hairlessLogin;
    }
}
