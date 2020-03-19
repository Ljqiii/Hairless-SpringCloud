package com.ljqiii.hairlessauth.controller;

import com.ljqiii.hairlesscommon.form.UserReg;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@ControllerAdvice
public class Auth {



    @PostMapping("/user/register")
    public String register(@Valid UserReg userReg) {
        return "ok";

    }
}
