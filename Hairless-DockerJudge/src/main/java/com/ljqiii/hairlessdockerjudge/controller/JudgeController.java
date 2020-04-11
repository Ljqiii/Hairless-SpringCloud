package com.ljqiii.hairlessdockerjudge.controller;


import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JudgeController {

    @PostMapping
    public HairlessResponse<Object> sumbitProblem() {
        return null;
    }

}
