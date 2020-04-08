package com.ljqiii.hairlessmain.controller;


import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscommon.vo.ProblemVO;
import com.ljqiii.hairlessmain.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @GetMapping("/problemlist")
    public HairlessResponse<PageData<List<ProblemListVO>>> problemList(
            @RequestParam(value = "category", required = false, defaultValue = "") String category,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount,
            Principal principal) {

        String usename = principal == null ? null : principal.getName();

        PageData<List<ProblemListVO>> problemListVOS = problemService.listProblem(usename, category.equals("all") ? null : category, pageNum, pageCount);
        HairlessResponse<PageData<List<ProblemListVO>>> response = new HairlessResponse<>();
        response.setCodeMsg(ResultEnum.OK);
        response.setData(problemListVOS);
        return response;
    }

    @GetMapping("/problem/{problemid}")
    public HairlessResponse<ProblemVO> probelm(
            Principal principal,
            @PathVariable(value = "problemid") Integer problemid) {
        HairlessResponse<ProblemVO> response = new HairlessResponse<>();
        boolean isVip = false;

        if (principal != null) {
            isVip = ((OAuth2Authentication) principal).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIP"));
        }

        boolean vipProblem = problemService.isVipProblem(problemid);
        if (vipProblem && principal == null) {
            response.setCodeMsg(ResultEnum.VIPPROBLEM_UNLOGIN);
            return response;
        } else if (vipProblem && (isVip == false)) {
            response.setCodeMsg(ResultEnum.VIP_ONLY);
            return response;
        }
        ProblemVO problem = problemService.getProblem(principal != null ? principal.getName() : null, problemid);
        if (problem == null) {
            response.setCodeMsg(ResultEnum.PROBLEM_DONOT_EXIST);
            return response;
        }
        response.setCodeMsg(ResultEnum.OK);
        response.setData(problem);

        return response;
    }
}
