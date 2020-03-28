package com.ljqiii.hairlesscommon.enums;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointEventEnum {
    LOGIN_EVERYDAY(1, "每日登录");

    int eventid;
    String description;
}
