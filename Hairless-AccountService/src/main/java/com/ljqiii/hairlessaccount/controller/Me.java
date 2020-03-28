package com.ljqiii.hairlessaccount.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlessaccount.client.NotificationClient;
import com.ljqiii.hairlessaccount.service.AccountService;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Me {


    @Autowired
    AccountService accountService;

    @Autowired
    NotificationClient notificationClient;

    @GetMapping("/pointdetails")
    public Principal pointDetails(Principal principal) {
        return principal;
    }


    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<JSONObject> a(Principal principal) {

        String name = principal.getName();
        accountService.sendLogin(name);

        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject data = new JSONObject();
        HairlessResponse<JSONObject> notificationCountResponse = notificationClient.unReadNotificationCount(name);
        if (!notificationCountResponse.hasError()) {
            data.put("unReadNotificationCount", notificationCountResponse.getData().getInteger("count"));
            response.ok();
        } else {
            response.setCodeMsg(ResultEnum.SERVER_ERROR);
        }
        response.setData(data);
        return response;
    }


//
//
//    @GetMapping("/me")
//    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
//    public HairlessResponse<JSONObject> me(Principal principal) {
//        String name = principal.getName();
//        HairlessResponse<JSONObject> response = new HairlessResponse<>();
//        JSONObject data = new JSONObject();
//
//        int count = notificationService.unReadNotificationCount(name);
//        data.put("unReadNotificationCount", count);
//        return response;
//    }


}
