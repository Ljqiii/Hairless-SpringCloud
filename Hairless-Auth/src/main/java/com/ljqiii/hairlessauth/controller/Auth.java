package com.ljqiii.hairlessauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.form.UserReg;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class Auth {

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Map<String, Object> getUser(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", user.getPrincipal());
        userInfo.put("authorities", user.getUserAuthentication().getAuthorities());

        return userInfo;
    }

    @PostMapping("/user/register")
    public String register(@Valid UserReg userReg) {
        return "ok";
    }
}
