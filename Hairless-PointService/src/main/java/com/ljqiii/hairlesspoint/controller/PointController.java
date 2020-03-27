package com.ljqiii.hairlesspoint.controller;


import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.PointEvent;
import com.ljqiii.hairlesspoint.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PointController {

    @Autowired
    PointService pointService;

    @PostMapping("/addpoint")
    public JSONObject addPoint(Principal principal, PointEvent pointEvent) {
        pointService.addPoint(1, 2);
        JSONObject response = new JSONObject();
        response.put("a", "b");
        return response;
    }
}
