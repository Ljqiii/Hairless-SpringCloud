package com.ljqiii.hairlessmain.controller;


import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlessmain.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HairlessResponse<Object> probelm(
            Principal principal,
            @PathVariable(value = "problemid") Integer problemid) {
        return null;
    }

}
