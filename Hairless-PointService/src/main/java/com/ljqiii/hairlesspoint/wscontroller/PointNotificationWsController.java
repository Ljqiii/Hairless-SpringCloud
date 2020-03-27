package com.ljqiii.hairlesspoint.wscontroller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PointNotificationWsController {


    @MessageMapping("/w")
    @SendTo("/topic/say")
    public Msg say(Msg msg) {
        System.out.println(msg);
        return new Msg("hi," + msg);
    }

}
