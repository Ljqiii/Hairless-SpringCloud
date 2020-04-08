package com.ljqiii.hairlesscommon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    int id;
    String title;
    String complexity;
    boolean onlyVip;
    String description;
    String dockerImage;
    String initCode;


}
