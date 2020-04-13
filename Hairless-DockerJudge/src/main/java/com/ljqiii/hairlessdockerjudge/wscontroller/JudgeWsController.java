package com.ljqiii.hairlessdockerjudge.wscontroller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
public class JudgeWsController {

    @MessageMapping("/r")
    @SendTo("/topic/say")
    public String restr(Principal principal, String r) {
        return "receive";
    }

    @MessageMapping("/all")
    public String commonWs() {
        return "all message";
    }

}
