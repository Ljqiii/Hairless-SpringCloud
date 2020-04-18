package com.ljqiii.hairlessforum.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;

@RestController
public class Ping {

    static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknow IpAddress";
        }
    }

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/public/ping")
    public String publicping(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        return "pong [public] from " + appName + ", ip:" + getIp();
    }

    @GetMapping("/ping")
    public String ping(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        return "pong from " + appName + ", ip:" + getIp();
    }
}
