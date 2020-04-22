package com.ljqiii.hairlesscommon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemTemplate {
    int id;
    String lang;
    String dockerImage;
    String cmd;
    String initcode;
    String dockerCacheDir;
}
