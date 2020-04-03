package com.ljqiii.hairlessaccount.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class Ping {

    @GetMapping("/public/ping")
    public String publicping(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        return "pong from account";
    }

    @GetMapping("/ping")
    public String ping(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        return "pong from account";
    }
}
