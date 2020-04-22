package com.ljqiii.hairlessmain.form;

import com.ljqiii.hairlesscommon.domain.ProblemCode;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class NewProblemForm {

    String title;
    String complexity;
    boolean onlyVip;
    String description;
    String dockerImage;
    ProblemCode initCode;

    Integer memoryLimit;
    String dockerCacheDir;
    String lang;
    String cmd;
}
