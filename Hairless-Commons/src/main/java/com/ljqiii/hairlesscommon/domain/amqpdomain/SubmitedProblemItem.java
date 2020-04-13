package com.ljqiii.hairlesscommon.domain.amqpdomain;

import com.ljqiii.hairlesscommon.domain.ProblemCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitedProblemItem {

    int submitId;
    String username;
    int problemId;

    String lang;
    ProblemCode problemCode;

    String imageName;//镜像名

    String cmdList[];

    boolean removeAfterExit;
}
