package com.ljqiii.hairlesscommon.vo.wsvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JudgeLogsMessage {
    int submitId;
    String oneLineLog;
}
