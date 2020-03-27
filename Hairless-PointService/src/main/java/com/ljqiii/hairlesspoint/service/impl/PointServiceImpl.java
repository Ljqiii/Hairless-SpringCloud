package com.ljqiii.hairlesspoint.service.impl;

import com.ljqiii.hairlesscommon.domain.Point;
import com.ljqiii.hairlesspoint.dao.PointMapper;
import com.ljqiii.hairlesspoint.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PointServiceImpl implements PointService {

    @Autowired
    PointMapper pointMapper;

    @Override
    public void addPoint(int userid, int pointeventid) {
        Point point = Point.builder().userid(userid).eventid(pointeventid).build();
        pointMapper.insertPoint(point);
    }

}
