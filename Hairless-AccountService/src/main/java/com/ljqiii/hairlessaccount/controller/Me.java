package com.ljqiii.hairlessaccount.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlessaccount.client.NotificationClient;
import com.ljqiii.hairlessaccount.form.NewPasswordForm;
import com.ljqiii.hairlessaccount.service.AccountService;
import com.ljqiii.hairlesscommon.constants.RoleConstants;
import com.ljqiii.hairlesscommon.domain.User;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            data.put("isvip", user.isVip());
            data.put("isAdmin", roles.contains(RoleConstants.Admin));
            data.put("isTeacher", roles.contains(RoleConstants.Teacher));
        } else {
            response.setCodeMsg(ResultEnum.SERVER_ERROR);
        }

        response.setData(data);
        return response;
    }

    @PostMapping("/changepassword")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<Void> changePassword(Principal principal,
                                                 @RequestBody NewPasswordForm newPasswordForm) {
        HairlessResponse<Void> response = new HairlessResponse<>();

        if (!newPasswordForm.getNewpassword().equals(newPasswordForm.getRepatednewpassword())) {
            response.setCodeMsg(ResultEnum.PASSEORD_NOT_SAME);
            return response;
        }
        accountService.changePassword(principal.getName(), newPasswordForm.getNewpassword());
        response.setCodeMsg(ResultEnum.CHANGE_PASSEORD_OK);
        return response;
    }

}
