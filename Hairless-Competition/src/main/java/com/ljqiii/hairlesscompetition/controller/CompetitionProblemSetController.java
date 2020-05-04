package com.ljqiii.hairlesscompetition.controller;

import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscompetition.aspectj.annotation.CompetitionPrivilege;
import com.ljqiii.hairlesscompetition.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class CompetitionProblemSetController {


    @Autowired
    CompetitionService competitionService;

    @GetMapping("/problemset")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    @CompetitionPrivilege
    public HairlessResponse<PageData<List<ProblemListVO>>> problemList(
            @RequestParam(value = "competitionid") Integer competitionId,
            Principal principal) {
        PageData<List<ProblemListVO>> problemSet = competitionService.getProblemSet(competitionId, principal.getName());
        HairlessResponse<PageData<List<ProblemListVO>>> response = new HairlessResponse<>();
        response.setCodeMsg(ResultEnum.OK);
        response.setData(problemSet);
        return response;
    }
}
