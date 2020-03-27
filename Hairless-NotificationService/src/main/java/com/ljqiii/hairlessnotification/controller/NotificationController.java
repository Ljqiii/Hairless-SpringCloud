package com.ljqiii.hairlessnotification.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.Notification;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlessnotification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/getunpush")
    List<Notification> getUnPushNotification(Principal principal) {
        System.out.println(principal);
        return null;
    }

    @GetMapping("/getunread")
    List<Notification> getUnreadNotification(Principal principal) {
        return null;
    }

    @GetMapping("/getunreadcount")
    HairlessResponse<JSONObject> getUnreadNotificationCount(Principal principal) {
        JSONObject data = new JSONObject();
        data.put("count", 132);
        HairlessResponse<JSONObject> response=new HairlessResponse<>();
        response.setCodeMsg(ResultEnum.OK);
        return response;
    }


}
