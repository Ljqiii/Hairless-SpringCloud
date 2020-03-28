package com.ljqiii.hairlesspoint.api;

import com.ljqiii.hairlesscommon.domain.Point;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesspoint.service.PointService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointApi {

    @Autowired
    PointService pointService;

    @PostMapping("/addpoint")
    void addPoint(@RequestBody Point point) {
        pointService.addPoint(point);

    }
}
