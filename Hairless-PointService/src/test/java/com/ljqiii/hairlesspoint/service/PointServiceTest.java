package com.ljqiii.hairlesspoint.service;

import com.ljqiii.hairlesscommon.domain.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointServiceTest {

    @Autowired
    PointService pointService;

    @Test
    public void addPoint() {
        pointService.addPoint(new Point(223, "sdf", new Date(), 1));

    }
}