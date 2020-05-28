package com.ljqiii.hairlessmain.controller;


import com.ljqiii.hairlesscommon.domain.ProblemAnswer;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlessmain.service.ProblemAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProblemAnswerController {

    @Autowired
    ProblemAnswerService problemAnswerService;

    @GetMapping("/getProblemAnswer")
    public HairlessResponse<List<ProblemAnswer>> problemAnswerList(
            @RequestParam(value = "problemId", required = true) Integer problemId,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount) {

        List<ProblemAnswer> problemAnswers = problemAnswerService.getProblemAnswers(problemId);
        HairlessResponse<List<ProblemAnswer>> response = new HairlessResponse<>();
        response.setCodeMsg(ResultEnum.OK);
        response.setData(problemAnswers);
        return response;
    }
}
