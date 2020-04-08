package com.ljqiii.hairlesscommon.domain;

import lombok.*;

import java.util.List;


public class ProblemCode {

    @Getter
    @Setter
    List<ProblemCodeFileItem> problemCodeFileItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class ProblemCodeFileItem {
        String type;
        String path;
        String filename;
        String content;
        List<ProblemCodeFileItem> children;
    }

}
