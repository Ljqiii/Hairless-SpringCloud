package com.ljqiii.hairlessauth.controller;

import com.ljqiii.hairlessauth.service.UserService;
import com.ljqiii.hairlessauth.form.UserReg;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class Auth {


    @Autowired
    UserService userService;


    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public String register(@Valid UserReg userReg) {
        userService.newUser(userReg);
        return "ok";
    }

    @GetMapping("/test")
    public String test(Principal principal, HttpServletRequest request) {
        return "test from auth server";
    }
}
