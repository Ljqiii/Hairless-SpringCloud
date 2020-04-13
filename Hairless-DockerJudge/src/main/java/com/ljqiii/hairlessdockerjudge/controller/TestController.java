package com.ljqiii.hairlessdockerjudge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/testsendtoser")
    public String testwssend() {
        simpMessagingTemplate.convertAndSendToUser("aaa", "/queue/judgeresult", "dsaf");
        return "ok";
    }

}
