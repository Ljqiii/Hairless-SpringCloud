package com.ljqiii.hairlessdockerjudge.form;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.ProblemCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSubmitForm implements Serializable {

    int problemId;
    ProblemCode problemCode;
}
