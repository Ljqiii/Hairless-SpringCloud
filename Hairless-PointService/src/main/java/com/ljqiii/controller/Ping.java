package com.ljqiii.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Ping {

    @GetMapping("/ping")
    public String a(Principal principal){
        Object credentials = ((OAuth2Authentication) principal).getCredentials();


        return "pong";
    }
}
