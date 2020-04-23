package com.ljqiii.hairlessdockerjudge.service;

import com.ljqiii.hairlessdockerjudge.comsumer.UserInfoComsumer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ljqiii.hairlessdockerjudge"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.ljqiii\\.hairlessdockerjudge\\.comsumer\\..*"))
public class TestExcludeConsumer {
}
