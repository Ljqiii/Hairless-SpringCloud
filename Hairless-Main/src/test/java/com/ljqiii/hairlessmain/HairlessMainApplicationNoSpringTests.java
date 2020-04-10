package com.ljqiii.hairlessmain;


import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.ProblemCode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

class HairlessMainApplicationNoSpringTests {

    @Test
    void contextLoads() {
        ProblemCode problemCode = new ProblemCode();

        ProblemCode.ProblemCodeFileItem folderSrc = ProblemCode.ProblemCodeFileItem.builder()
                .filename("src")
                .path("/")
                .type("folder")
                .build();
        ProblemCode.ProblemCodeFileItem folderSrcMain = ProblemCode.ProblemCodeFileItem.builder()
                .filename("main")
                .path("/src")
                .type("folder")
                .build();
        ProblemCode.ProblemCodeFileItem com = ProblemCode.ProblemCodeFileItem.builder()
                .filename("com")
                .path("/src/main")
                .type("folder")
                .build();
        ProblemCode.ProblemCodeFileItem ljq = ProblemCode.ProblemCodeFileItem.builder()
                .filename("ljq")
                .path("/src/main/com")
                .type("folder")
                .build();


        ProblemCode.ProblemCodeFileItem mainjava = ProblemCode.ProblemCodeFileItem.builder()
                .filename("main.java")
                .path("/src/main")
                .type("file")
                .content("main.java")
                .build();

        com.setChildren(new TreeSet<>(Arrays.asList(ljq)));
        folderSrc.setChildren(new TreeSet<>(Arrays.asList(folderSrcMain)));
        folderSrcMain.setChildren(new TreeSet<>(Arrays.asList(com)));
        ljq.setChildren(new TreeSet<>(Arrays.asList(mainjava)));


        ProblemCode.ProblemCodeFileItem readme = ProblemCode.ProblemCodeFileItem.builder()
                .filename("readme")
                .path("/")
                .type("file")
                .content("readme")
                .build();


        problemCode.setProblemCodeFileItems(new TreeSet<>(Arrays.asList(readme, folderSrc)));


        String s = JSONObject.toJSONString(problemCode);
        String spretty = JSONObject.toJSONString(problemCode,true);
        int a = 1;

    }

}
