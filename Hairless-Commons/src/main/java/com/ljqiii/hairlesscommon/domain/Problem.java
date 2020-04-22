package com.ljqiii.hairlesscommon.domain;

import com.ljqiii.hairlesscommon.constants.LangConstants;
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

    Integer memoryLimit;
    String dockerCacheDir;
    String lang;
    String cmd;
    String ownerUserName;
}
