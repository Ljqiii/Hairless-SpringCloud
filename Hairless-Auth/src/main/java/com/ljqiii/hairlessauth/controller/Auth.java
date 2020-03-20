package com.ljqiii.hairlessauth.controller;

import com.ljqiii.hairlesscommon.form.UserReg;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@ControllerAdvice
public class Auth {


    @PostMapping("/me")
    public Principal me(Principal principal) {
        return principal;
    }


    @PostMapping("/user/register")
    public String register(@Valid UserReg userReg) {
        return "ok";

    }
}
