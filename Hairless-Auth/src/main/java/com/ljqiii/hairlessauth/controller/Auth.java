package com.ljqiii.hairlessauth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlessauth.dao.UserMapper;
import com.ljqiii.hairlessauth.dao.VipBillMapper;
import com.ljqiii.hairlessauth.service.UserService;
import com.ljqiii.hairlessauth.form.UserReg;
import com.ljqiii.hairlesscommon.constants.RoleConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class Auth {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    VipBillMapper vipBillMapper;

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public JSONObject getUser(Principal principal) {
        JSONObject principaljson = new JSONObject();

        if (principal == null) {
            return principaljson;

        } else if (principal instanceof OAuth2Authentication
                && ((OAuth2Authentication) principal).isClientOnly() == false) {//clientonly
            //每次请求都重新加载role，为了会员
            JSONArray authorities = new JSONArray();
            userMapper.selectUserByUserName(principal.getName()).getAuthorities().stream().forEach(r -> {
                JSONObject temp = new JSONObject();
                temp.put("authority", r.getName());
                authorities.add(temp);
            });

            //是不是vip
            if (vipBillMapper.ifUserVipNow(principal.getName()) >= 1) {
                JSONObject ROLE_VIP = new JSONObject();
                ROLE_VIP.put("authority", RoleConstants.Vip);
                authorities.add(ROLE_VIP);
            }
            principaljson = JSONObject.parseObject(JSONObject.toJSONString(principal));
            principaljson.put("authorities", authorities);
            return principaljson;

        } else {
            return JSONObject.parseObject(JSONObject.toJSONString(principal));
        }

    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public String register(@Valid UserReg userReg) {
        userService.newUser(userReg);
        return "ok";
    }

    @GetMapping("/test")
    public String test(Principal principal, HttpServletRequest request) {
        return "test from auth server";
    }
}
