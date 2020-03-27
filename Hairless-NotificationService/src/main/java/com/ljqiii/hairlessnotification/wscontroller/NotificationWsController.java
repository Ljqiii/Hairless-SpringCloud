package com.ljqiii.hairlessnotification.wscontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationWsController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/w")
    @SendTo("/topic/say")
    public Msg say(Msg msg) {
        System.out.println(msg);
        return new Msg("hi," + msg);
    }

}
