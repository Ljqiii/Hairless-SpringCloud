package com.ljqiii.hairlesscompetition.controller;

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
    public HairlessResponse<HashMap<String, String>> calc(@RequestParam("competitionId") int competitionId) {
        HairlessResponse<HashMap<String, String>> response = new HairlessResponse<>();
        HashMap<String, String> leaderBoard = competitionLeaderService.getLeaderBoard(competitionId);
        response.setCodeMsg(ResultEnum.OK);
        response.setData(leaderBoard);
        return response;
    }
}
