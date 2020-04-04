package com.ljqiii.hairlessmain.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Problem {


    @GetMapping("/pointdetails")
    public Principal pointDetails(Principal principal) {
        return principal;
    }

}
