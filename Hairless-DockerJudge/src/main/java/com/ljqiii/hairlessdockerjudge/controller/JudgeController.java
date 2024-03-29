package com.ljqiii.hairlessdockerjudge.controller;


import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.apiform.AddCompetitionSubmitForm;
import com.ljqiii.hairlesscommon.constants.RoleConstants;
import com.ljqiii.hairlesscommon.enums.JudgePriorityEnum;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlessdockerjudge.client.CompetitionSubmitClient;
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

    @Autowired
    CompetitionSubmitClient competitionSubmitClient;

    //添加分布式事务

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

        if (problemSubmitForm.getCompetitionId() != null) {
            //TODO:验证竞赛是否存在
            //TODO:验证是否加入竞赛
        }

        boolean isVip = authorithies.contains(RoleConstants.Vip);
        boolean problemVipOnly = dockerJudgeService.ifProblemVipOnly(problemSubmitForm.getProblemId());
        if (isVip == false && problemVipOnly == true) {
            response.setCodeMsg(ResultEnum.VIP_ONLY);
            return response;
        }

        int submitId = dockerJudgeService.submitCode(principal.getName(),
                problemSubmitForm.getProblemId(),
                problemSubmitForm.getProblemCode(),
                authorithies.contains(RoleConstants.Vip) ? JudgePriorityEnum.VIP : JudgePriorityEnum.NORMAL);//有vip权限高优先级

        //添加提交id到竞赛
        if (problemSubmitForm.getCompetitionId() != null) {
            competitionSubmitClient.addSubmit(
                    AddCompetitionSubmitForm.builder()
                            .competitionId(problemSubmitForm.getCompetitionId())
                            .submitId(submitId).build());
        }
        response.setCodeMsg(ResultEnum.OK);
        JSONObject responseData = new JSONObject();
        responseData.put("submitid", submitId);
        response.setData(responseData);

        return response;
    }


}
