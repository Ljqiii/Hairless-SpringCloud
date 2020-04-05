package com.ljqiii.hairlesscommon.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemListVO {
    Boolean isResolve;//当前user是否解决此问题
    int id;
    String title;
    String complexity;//难易程度
    String acceptance;//正确率
    int discusscount;//讨论量
}
