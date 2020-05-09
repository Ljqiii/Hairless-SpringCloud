package com.ljqiii.hairlesscompetition.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscompetition.service.CompetitionLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class CompetitionLeaderBoardController {

    @Autowired
    CompetitionLeaderService competitionLeaderService;

    @GetMapping("/getLeaderBoard")
    public HairlessResponse<HashMap<String, JSONArray>> calc(@RequestParam("competitionId") int competitionId) {
        HairlessResponse<HashMap<String, JSONArray>> response = new HairlessResponse<>();
        HashMap<String, String> leaderBoard = competitionLeaderService.getLeaderBoard(competitionId);
        HashMap<String, JSONArray> leaderBoardJson = new HashMap<>();
        leaderBoardJson.put("tablecontentjson", JSON.parseArray(leaderBoard.get("tablecontentjson")));
        leaderBoardJson.put("tablemetajson", JSON.parseArray(leaderBoard.get("tablemetajson")));

        response.setCodeMsg(ResultEnum.OK);
        response.setData(leaderBoardJson);
        return response;
    }
}
