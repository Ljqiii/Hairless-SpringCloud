package com.ljqiii.hairlesscompetition.aspectj.annotation;


import java.lang.annotation.*;


/**
 * 验证用户是否加入了竞赛,请求param中需要有参数competitionid，必须登录，任何一个缺少则跳过
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CompetitionPrivilege {


}
