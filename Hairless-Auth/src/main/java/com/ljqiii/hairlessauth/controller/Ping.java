package com.ljqiii.hairlessauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Ping {

    @GetMapping("/ping")
    public String a(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = new Cookie("pingcookiek", "pingcookiev");
        response.addCookie(cookie);
        return "pong";
    }

    @GetMapping("/authenticatedtest")
    public String authenticatedTest() {
        return "pong";
    }
}
