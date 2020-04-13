package com.ljqiii.hairlessdockerjudge.controller;


import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.constants.RoleConstants;
import com.ljqiii.hairlesscommon.domain.ProblemCode;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlessdockerjudge.form.ProblemSubmitForm;
import com.ljqiii.hairlessdockerjudge.service.DockerJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JudgeController {

    @Autowired
    DockerJudgeService dockerJudgeService;

    @PostMapping("/submit")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<JSONObject> sumbitProblem(Principal principal,
                                                      @RequestBody ProblemSubmitForm problemSubmitForm) {
        List<String> authorithies = ((OAuth2Authentication) principal)
                .getUserAuthentication()
                .getAuthorities()
                .stream()
                .map(s -> s.getAuthority())
                .collect(Collectors.toList());

        HairlessResponse<JSONObject> response = new HairlessResponse<>();

        boolean isVip = authorithies.contains(RoleConstants.Vip);
        boolean problemVipOnly = dockerJudgeService.ifProblemVipOnly(problemSubmitForm.getProblemId());
        if (isVip == false && problemVipOnly == true) {
            response.setCodeMsg(ResultEnum.VIP_ONLY);
            return response;
        }

        int submitId = dockerJudgeService.submitCode(principal.getName(),
                problemSubmitForm.getProblemId(),
                problemSubmitForm.getProblemCode());

        response.setCodeMsg(ResultEnum.OK);
        JSONObject responseData = new JSONObject();
        responseData.put("submitid", submitId);
        response.setData(responseData);

        return response;
    }


}
