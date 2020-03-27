package com.ljqiii.hairlessaccount.service.impl;

import com.ljqiii.hairlesscommon.domain.Point;
import com.ljqiii.hairlessaccount.dao.PointMapper;
import com.ljqiii.hairlessaccount.service.PointService;
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
