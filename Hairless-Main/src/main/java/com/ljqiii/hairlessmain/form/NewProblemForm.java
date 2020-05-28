package com.ljqiii.hairlessmain.form;

import com.ljqiii.hairlesscommon.domain.ProblemCode;
import lombok.Data;

import java.util.List;

@Data
public class NewProblemForm {

    String title;
    String complexity;
    String answer;
    boolean onlyVip;
    String description;
    String dockerImage;
    ProblemCode initCode;

    Integer memoryLimit;
    String dockerCacheDir;
    String lang;
    String cmd;
    List<Integer> categoryids;
}
