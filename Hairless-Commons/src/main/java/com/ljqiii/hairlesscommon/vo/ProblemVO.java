package com.ljqiii.hairlesscommon.vo;

import com.ljqiii.hairlesscommon.domain.ProblemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemVO {
    int id;
    String title;
    boolean isLike;//当前用户是否喜欢
    String description;//描述
    String complexity;//难易程度
    String acceptance;//正确率
    Long discusscount;//讨论量
    int favoriteCount;//喜欢数量
    int sumbitedCount;//提交数量
    ProblemCode initProblemCode;//代码
}
