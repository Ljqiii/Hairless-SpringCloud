package com.ljqiii.hairlesscompetition.controller;

import com.ljqiii.hairlesscompetition.service.CompetitionLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    CompetitionLeaderService competitionLeaderService;

    //TODO:delete this after test
    @GetMapping("/calc")
    public String calc(@RequestParam("id") int competitionId) {
        competitionLeaderService.calcLeaderBoard(competitionId);
        return "started";
    }
}
