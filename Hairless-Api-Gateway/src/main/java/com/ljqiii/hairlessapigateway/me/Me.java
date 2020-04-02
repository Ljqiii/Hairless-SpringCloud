package com.ljqiii.hairlessapigateway.me;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Me {


    @GetMapping("/me")
    public Principal me(Principal principal) {
        return principal;
    }
}
