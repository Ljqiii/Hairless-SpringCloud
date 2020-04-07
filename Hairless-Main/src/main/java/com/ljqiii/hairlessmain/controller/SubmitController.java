package com.ljqiii.hairlessmain.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlessmain.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
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

    //准确率
    @GetMapping("/accuracy")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<List<JSONObject>> accuracy(Principal principal) {
        String name = principal.getName();
        HashMap<String, Integer> accuracyData = submitService.accuracyData(name);
        HairlessResponse<List<JSONObject>> response = new HairlessResponse<>();
        List<JSONObject> resultList = new ArrayList<>();
        JSONObject successcount = new JSONObject();
        successcount.put("name", "正确");
        successcount.put("value", accuracyData.get("successcount"));
        JSONObject errorcount = new JSONObject();
        errorcount.put("name", "错误");
        errorcount.put("value", accuracyData.get("errorcount"));
        resultList.add(successcount);
        resultList.add(errorcount);
        response.setCodeMsg(ResultEnum.OK);
        response.setData(resultList);
        return response;
    }
}
