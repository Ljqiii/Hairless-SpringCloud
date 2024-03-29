package com.ljqiii.hairlessmain.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.domain.ProblemAnswer;
import com.ljqiii.hairlesscommon.domain.ProblemTemplate;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscommon.vo.ProblemVO;
import com.ljqiii.hairlessmain.form.NewProblemForm;
import com.ljqiii.hairlessmain.service.ProblemAnswerService;
import com.ljqiii.hairlessmain.service.ProblemService;
import com.ljqiii.hairlessmain.service.ProblemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProblemController {

    @Autowired
    ProblemService problemService;
    @Autowired
    ProblemTemplateService problemTemplateService;
    @Autowired
    ProblemAnswerService problemAnswerService;


    @GetMapping("/problemlist")
    public HairlessResponse<PageData<List<ProblemListVO>>> problemList(
            @RequestParam(value = "category", required = false, defaultValue = "") String category,
            @RequestParam(value = "owner", required = false, defaultValue = "") String owner,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount,
            Principal principal) {

        String usename = principal == null ? null : principal.getName();

        PageData<List<ProblemListVO>> problemListVOS = problemService.listProblem(owner, usename, category.equals("all") ? null : category, pageNum, pageCount);
        HairlessResponse<PageData<List<ProblemListVO>>> response = new HairlessResponse<>();
        response.setCodeMsg(ResultEnum.OK);
        response.setData(problemListVOS);
        return response;
    }

    @GetMapping("/problemtitle/{problemid}")
    public HairlessResponse<JSONObject> probelmTitle(@PathVariable(value = "problemid") String problemid) {
        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        try {
            Integer.valueOf(problemid);
        } catch (Exception e) {
            response.setCodeMsg(ResultEnum.PEOBLEM_ID_INVALID);
            return response;
        }
        ProblemVO problem = problemService.getProblem(null, Integer.valueOf(problemid));
        JSONObject jsonObject = new JSONObject();
        if (problem == null) {
            response.setCodeMsg(ResultEnum.PROBLEM_DONOT_EXIST);
        } else {
            response.setCodeMsg(ResultEnum.OK);
            jsonObject.put("id", problem.getId());
            jsonObject.put("title", problem.getTitle());
            response.setData(jsonObject);
        }
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
        } else if (vipProblem && (!isVip)) {
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


    @PostMapping("/newproblem")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_TEACHER')")
    @Transactional
    public HairlessResponse<JSONObject> newProblem(
            @RequestBody NewProblemForm form,
            Principal principal) {

        List<Category> categories = new ArrayList<>();
        form.getCategoryids().stream().forEach(item -> categories.add(new Category(item, null, null)));

        Problem problem = Problem.builder()
                .dockerImage(form.getDockerImage())
                .lang(form.getLang())
                .cmd(form.getCmd())
                .description(form.getDescription())
                .initCode(JSON.toJSONString(form.getInitCode()))
                .complexity(form.getComplexity())
                .title(form.getTitle())
                .onlyVip(form.isOnlyVip())
                .dockerCacheDir(form.getDockerCacheDir())
                .memoryLimit(form.getMemoryLimit())
                .ownerUserName(principal.getName())
                .build();

        Integer problemId = problemService.newProblem(problem, categories);
        problemAnswerService.newProblemAnswer(
                ProblemAnswer.builder().problemId(problemId).answercontent(form.getAnswer()).username(principal.getName()).build());

        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject data = new JSONObject();
        data.put("problemid", problemId);
        response.setCodeMsg(ResultEnum.OK);
        response.setData(data);
        return response;
    }

    @GetMapping("/problemtemple")
    public HairlessResponse<ProblemTemplate> problemList(
            @RequestParam(value = "lang", required = false, defaultValue = "java1.8/maven") String lang) {
        ProblemTemplate template = problemTemplateService.getTemplate(lang);
        HairlessResponse<ProblemTemplate> response = new HairlessResponse<>();
        response.setCodeMsg(ResultEnum.OK);
        response.setData(template);
        return response;
    }
}
