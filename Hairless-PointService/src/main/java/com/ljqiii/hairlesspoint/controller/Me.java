package com.ljqiii.hairlesspoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Me {

    @GetMapping("/me")
    public Principal principal(Principal principal) {
        return principal;
    }
}
