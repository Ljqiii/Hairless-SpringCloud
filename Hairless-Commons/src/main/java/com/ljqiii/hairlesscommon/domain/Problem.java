package com.ljqiii.hairlesscommon.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Problem {
    int id;
    String title;
    String complexity;

    String description;
    String dockerImage;
    String initCode;


}
