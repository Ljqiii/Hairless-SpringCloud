package com.ljqiii.hairlesscommon.apiform;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetProblemVoForm {
    List<Integer> problemids;
    String username;
    //分页
}
