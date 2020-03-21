package com.ljqiii.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PointController {

    @PreAuthorize("hasRole('normaluser')")
    public String addPoint(String a, Principal principal) {
        System.out.println(principal);
        return "aaa";

    }


}
