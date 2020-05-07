package com.ljqiii.hairlesscompetition.controller;

import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.SubmitedItemVO;
import com.ljqiii.hairlesscompetition.aspectj.annotation.CompetitionPrivilege;
import com.ljqiii.hairlesscompetition.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class CompetitionSubmitSetController {
    @Autowired
    SubmitService submitService;

    @GetMapping("/submitsset")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    @CompetitionPrivilege
    public HairlessResponse<List<SubmitedItemVO>> problemList(
            @RequestParam(value = "competitionid") Integer competitionId,
            Principal principal) {
        List<SubmitedItemVO> submits = submitService.getSubmits(competitionId, principal.getName());

        HairlessResponse<List<SubmitedItemVO>> response = new HairlessResponse<>();
        response.setCodeMsg(ResultEnum.OK);
        response.setData(submits);
        return response;
    }
}
