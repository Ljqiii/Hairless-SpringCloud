package com.ljqiii.hairlesspoint.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Me {

    @GetMapping("/pointdetails")
    public Principal pointDetails(Principal principal) {
        return principal;
    }
    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public Principal a(Principal principal){
        return principal;
    }
}
