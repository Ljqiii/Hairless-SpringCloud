package com.ljqiii.hairlesscommon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Submit {
    int id;
    int problemid;
    String username;
    String result;
    String submitedCode;
    Date submitedTime;
    String judgeClient;
}
