package com.ljqiii.hairlessaccount.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlessaccount.client.NotificationClient;
import com.ljqiii.hairlessaccount.service.AccountService;
import com.ljqiii.hairlessaccount.service.VipBillService;
import com.ljqiii.hairlesscommon.constants.RoleConstants;
import com.ljqiii.hairlesscommon.domain.User;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Me {


    @Autowired
    AccountService accountService;

    @Autowired
    NotificationClient notificationClient;

    @Autowired
    VipBillService vipBillService;

    @GetMapping("/pointdetails")
    public Principal pointDetails(Principal principal) {
        return principal;
    }


    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<JSONObject> a(Principal principal) {
        String name = principal.getName();
        List<String> roles = ((OAuth2Authentication) principal).getUserAuthentication().getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());

        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject data = new JSONObject();

        data.put("username", name);
        HairlessResponse<JSONObject> notificationCountResponse = notificationClient.unReadNotificationCount(name);
        if (!notificationCountResponse.hasError()) {
            data.put("unReadNotificationCount", notificationCountResponse.getData().getInteger("count"));
            response.ok();

            User user = accountService.getUserByUserName(name);
            data.put("avatar", user.getAvatar());
            data.put("nickname", user.getNickName());
            data.put("isvip", vipBillService.isVipNow(user.getUserName()));
            data.put("isAdmin", roles.contains(RoleConstants.Admin));
        } else {
            response.setCodeMsg(ResultEnum.SERVER_ERROR);
        }

        response.setData(data);
        return response;
    }


}
