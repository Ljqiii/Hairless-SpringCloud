package com.ljqiii.hairlessauth.controller;

import com.ljqiii.hairlessauth.dao.UserMapper;
import com.ljqiii.hairlessauth.service.UserService;
import com.ljqiii.hairlesscommon.form.UserReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class Auth {


    @Autowired
    UserService userService;


    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Map<String, Object> getUser(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", user.getPrincipal());
        userInfo.put("authorities", user.getUserAuthentication().getAuthorities());

        return userInfo;
    }

    @PostMapping("/user/register")
    public String register(@Valid UserReg userReg) {
        userService.newUser(userReg);
        return "ok";

    }
}
