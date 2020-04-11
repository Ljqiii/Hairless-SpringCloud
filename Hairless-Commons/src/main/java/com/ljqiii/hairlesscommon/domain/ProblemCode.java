package com.ljqiii.hairlesscommon.domain;

import lombok.*;

import java.util.List;
import java.util.TreeSet;


public class ProblemCode {

    @Getter
    @Setter
    TreeSet<ProblemCodeFileItem> problemCodeFileItems;


    public void addChildren(ProblemCodeFileItem problemCodeFileItem) {
        if (this.problemCodeFileItems == null) {
            problemCodeFileItems = new TreeSet<ProblemCodeFileItem>();
        }
        problemCodeFileItems.add(problemCodeFileItem);
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class ProblemCodeFileItem implements Comparable<ProblemCodeFileItem> {
        String type;
        String path;
        String filename;
        boolean readOnly;//前端是否只读
        String content = "";
        TreeSet<ProblemCodeFileItem> children;

        String writeWhilteList[];

        public void addChildren(ProblemCodeFileItem problemCodeFileItem) {
            if (this.children == null) {
                children = new TreeSet<ProblemCodeFileItem>();
            }
            children.add(problemCodeFileItem);
        }


        //保证folder排在前边
        @Override
        public int compareTo(ProblemCodeFileItem o) {
            //如果type相同,比较filename
            if (o.type.equals(type)) {
                return filename.compareTo(o.type);
            } else {
                //否则folder排在前边
                if (type.equals("folder")) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

}
