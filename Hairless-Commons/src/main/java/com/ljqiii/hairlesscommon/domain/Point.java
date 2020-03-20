package com.ljqiii.hairlesscommon.domain;

import lombok.Data;
import java.sql.Time;

@Data
public class Point {
    int id;
    int userid;
    Time time;
    int eventid;
}
