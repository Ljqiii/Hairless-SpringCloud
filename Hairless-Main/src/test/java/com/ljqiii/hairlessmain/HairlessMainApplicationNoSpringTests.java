package com.ljqiii.hairlessmain;


import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.ProblemCode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

class HairlessMainApplicationNoSpringTests {

    @Test
    void contextLoads() {
        ProblemCode problemCode = new ProblemCode();

        ProblemCode.ProblemCodeFileItem item = ProblemCode.ProblemCodeFileItem.builder()
                .filename("file")
                .path("/")
                .type("file")
                .children(Arrays.asList(ProblemCode.ProblemCodeFileItem.builder().path("/a").build()))
                .build();


        ProblemCode.ProblemCodeFileItem item2 = ProblemCode.ProblemCodeFileItem.builder()
                .filename("file")
                .path("/")
                .type("file")
                .build();
        problemCode.setProblemCodeFileItems(Arrays.asList(item, item2));


        String s = JSONObject.toJSONString(problemCode);
        int a = 1;

    }

}
