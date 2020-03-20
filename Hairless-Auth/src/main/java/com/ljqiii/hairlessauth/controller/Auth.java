package com.ljqiii.hairlessauth.controller;

import com.ljqiii.hairlessauth.service.UserService;
import com.ljqiii.hairlesscommon.form.UserReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@ControllerAdvice
public class Auth {

    @Autowired
    UserService userService;

    @GetMapping("/user/test")
    @PreAuthorize("hasRole('normaluser')")
    public String gettest(Principal principal) {
        System.out.println(principal);
        return "test";
    }


    @PostMapping("/user/register")
    public String register(@Valid UserReg userReg) {
        userService.newUser(userReg);
        return "ok";

    }
}
