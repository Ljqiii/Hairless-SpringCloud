package com.ljqiii.hairlesscommon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JudgePriorityEnum {

    NORMAL(5),//正常用户
    VIP(6);//vip用户

    int priority;
}
