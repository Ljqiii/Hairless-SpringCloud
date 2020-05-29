package com.ljqiii.hairlessmain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Auth {

    @PostMapping("/clearSession")
    public ModelAndView clearJESSIONID(HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = new Cookie("SESSION", "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        cookie.setMaxAge(0);


        response.addCookie(cookie);

        modelAndView.setViewName("redirect:" + "http://hairlessauth.ljqiii.xyz:7081/clearJSESSIONID");
        return modelAndView;
    }
}
