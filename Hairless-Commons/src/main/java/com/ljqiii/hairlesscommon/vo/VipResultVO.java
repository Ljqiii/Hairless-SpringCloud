package com.ljqiii.hairlesscommon.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VipResultVO {
    String payeremail;
    String payername;
    String vipendtime;
    String paymethod;
}
