package com.ljqiii.hairlesscommon.vo.wsvo;

import com.ljqiii.hairlesscommon.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JudgeStepMessage {
    int submitId;
    String event;
    String flag;//ok,warning,error
}
