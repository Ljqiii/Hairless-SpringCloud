package com.ljqiii.hairlessmain.controller;

import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlessmain.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubmitController {

    @Autowired
    SubmitService submitService;

    @GetMapping("/correctleaderboard")
    public HairlessResponse<List<CorrectLeaderboard>> correctLeaderboard() {
        HairlessResponse<List<CorrectLeaderboard>> response = new HairlessResponse<>();
        java.util.List<CorrectLeaderboard> correctLeaderboards = submitService.correctLeaderboard();
        response.setCodeMsg(ResultEnum.OK);
        response.setData(correctLeaderboards);
        return response;
    }

}
